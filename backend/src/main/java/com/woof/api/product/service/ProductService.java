package com.woof.api.product.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.woof.api.common.BaseResponse;
import com.woof.api.member.exception.MemberAccountException;
import com.woof.api.member.model.entity.Member;
import com.woof.api.product.model.response.ProductFileDto;
import com.woof.api.product.model.entity.ProductManager;
import com.woof.api.product.model.entity.ProductSchool;
import com.woof.api.product.model.entity.ProductImage;

import com.woof.api.product.model.request.ProductManagerCreateReq;
import com.woof.api.product.model.request.ProductManagerUpdateReq;
import com.woof.api.product.model.request.ProductSchoolCreateReq;
import com.woof.api.product.model.request.ProductSchoolUpdateReq;
import com.woof.api.product.model.response.*;
import com.woof.api.product.repository.ProductImageRepository;
import com.woof.api.product.repository.ProductManagerRepository;
import com.woof.api.product.repository.ProductSchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductSchoolRepository productSchoolRepository;
    private final ProductManagerRepository productManagerRepository;
    private final ProductImageRepository productImageRepository;
    private final AmazonS3 s3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public BaseResponse<ProductManagerCreateResult> createManager(ProductManagerCreateReq productManagerCreateReq) {
        Member manager = ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (manager.getAuthority().substring(5).equals("MANAGER")) {

            ProductManager productManager = ProductManager.builder()
                    .managerName(manager.getMemberName())             // 닉네임이 맞는지 (실명 안해도 되려나요)
                    .gender(productManagerCreateReq.getGender())    // 혹은 매니저 entity에 추가되면 거기서 가져오기
                    .businessNum(productManagerCreateReq.getBusinessNum()) // 혹 매 e 추 거 가
                    .price(productManagerCreateReq.getPrice())      // 혹은 매니저 entity에 추가되면 거기서 가져오기
                    .career(productManagerCreateReq.getCareer())    // 혹은 매니저 entity에 추가되면 거기서 가져오기
                    .contents(productManagerCreateReq.getContents())// 혹은 매니저 entity에 추가되면 거기서 가져오기
                    .status(0)
                    .build();
            productManagerRepository.save(productManager);

            ProductManagerCreateResult productManagerCreateResult = ProductManagerCreateResult.builder().build();
            productManagerCreateResult.setIdx(productManager.getIdx());

            return BaseResponse.successRes("PRODUCT_001", true, "매니저가 등록되었습니다.", productManagerCreateResult);
        } else {
            throw MemberAccountException.forInvalidAuthority();
        }
    }

    @Transactional
    public BaseResponse<List<ProductManagerReadRes>> listManager() {
        List<ProductManager> result = productManagerRepository.findByStatus(1);
        List<ProductManagerReadRes> productManagerReadResList = new ArrayList<>();

        for (ProductManager productManager : result) {
            List<ProductImage> productImages = productManager.getProductImages();
            String filename = "";

            if (!productImages.isEmpty()) {
                ProductImage firstImage = productImages.get(0);
                filename = generatePresignedUrl(firstImage.getFilename(), firstImage.getFilename());
            }

            ProductManagerReadRes productManagerReadRes = ProductManagerReadRes.builder()
                    .idx(productManager.getIdx())
                    .managerName(productManager.getManagerName())
                    .gender(productManager.getGender())
                    .businessNum(productManager.getBusinessNum())
                    .price(productManager.getPrice())
                    .career(productManager.getCareer())
                    .contents(productManager.getContents())
                    .filename(filename)  // 첫 번째 사진 파일명 추가
                    .build();

            productManagerReadResList.add(productManagerReadRes);
        }

        return BaseResponse.successRes("PRODUCT_002", true, "매니저 목록 조회 성공.", productManagerReadResList);
    }

    @Transactional
    public BaseResponse<List<ProductManagerReadRes>> adminListManager() {
        List<ProductManager> result = productManagerRepository.findAll();
        List<ProductManagerReadRes> productManagerReadResList = new ArrayList<>();

        for (ProductManager productManager : result) {
            List<ProductImage> productImages = productManager.getProductImages();
            String filename = "";

            if (!productImages.isEmpty()) {
                ProductImage firstImage = productImages.get(0);
                filename = generatePresignedUrl(firstImage.getFilename(), firstImage.getFilename());
            }

            ProductManagerReadRes productManagerReadRes = ProductManagerReadRes.builder()
                    .idx(productManager.getIdx())
                    .managerName(productManager.getManagerName())
                    .gender(productManager.getGender())
                    .businessNum(productManager.getBusinessNum())
                    .price(productManager.getPrice())
                    .career(productManager.getCareer())
                    .contents(productManager.getContents())
                    .filename(filename)
                    .build();

            productManagerReadResList.add(productManagerReadRes);
        }

        return BaseResponse.successRes("PRODUCT_003", true, "관리자용 매니저 목록 조회 성공.", productManagerReadResList);
    }


    @Transactional
    public BaseResponse<ProductManagerReadRes> readManager(Long idx) {
        ProductManager productManager = productManagerRepository.findByIdx(idx)
                .orElseThrow(() -> new RuntimeException("해당 idx의 매니저를 찾을 수 없습니다."));

        ProductManagerReadRes productManagerReadRes = ProductManagerReadRes.builder()
                .idx(productManager.getIdx())
                .managerName(productManager.getManagerName())
                .gender(productManager.getGender())
                .businessNum(productManager.getBusinessNum())
                .price(productManager.getPrice())
                .career(productManager.getCareer())
                .contents(productManager.getContents())
                .build();

        return BaseResponse.successRes("PRODUCT_004", true, "매니저 조회 성공.", productManagerReadRes);
    }

    public BaseResponse<Void> updateManager(ProductManagerUpdateReq productManagerUpdateReq) {
        ProductManager productManager = productManagerRepository.findById(productManagerUpdateReq.getIdx())
                .orElseThrow(() -> new RuntimeException("해당 idx의 매니저를 찾을 수 없습니다."));

        productManager.setManagerName(productManagerUpdateReq.getManagerName());
        productManager.setGender(productManagerUpdateReq.getGender());
        productManager.setBusinessNum(productManagerUpdateReq.getBusinessNum());
        productManager.setPrice(productManagerUpdateReq.getPrice());
        productManager.setCareer(productManagerUpdateReq.getCareer());
        productManager.setContents(productManagerUpdateReq.getContents());

        productManagerRepository.save(productManager);

        return BaseResponse.successRes("PRODUCT_005", true, "매니저 정보 수정 성공.", null);
    }

    @Transactional
    public BaseResponse<Void> deleteManager(Long idx) {
        // 해당 idx의 ProductManagerImage를 한 번에 삭제
        productImageRepository.deleteAllByProductManagerIdx(idx);
        // ProductManager의 status를 2로 변경
        ProductManager productManager = productManagerRepository.findByIdx(idx).get();
        productManager.setStatus(2);

        return BaseResponse.successRes("PRODUCT_006", true, "매니저 삭제 성공.", null);
    }

    @Transactional
    public List<ProductFileDto> listFilesByProductManagerIdx(Long productManagerIdx) {
        // Board 엔티티의 ID를 기반으로 파일 목록 조회
        List<ProductImage> productImages = productImageRepository.findByProductManagerIdx(productManagerIdx);

        // 조회된 파일 목록을 BoardFileDto 리스트로 변환
        List<ProductFileDto> fileDtos = productImages.stream().map(file -> {
            // 각 파일에 대한 Presigned URL 생성
            String downloadUrl = generatePresignedUrl(file.getFilename(), file.getOriginalFilename());

            // 파일의 원본 이름과 다운로드 URL을 사용하여 GooutFileDto 객체 생성
            return new ProductFileDto(file.getIdx(), file.getOriginalFilename(), downloadUrl);
        }).collect(Collectors.toList());

        return fileDtos;
    }

    @Transactional
    public BaseResponse<Void> checkManager(Long idx) {
        ProductManager productManager = productManagerRepository.findByIdx(idx)
                .orElseThrow(() -> new RuntimeException("해당 idx의 업체 정보를 찾을 수 없습니다."));
        productManager.setStatus(1);
        productManagerRepository.save(productManager);

        return BaseResponse.successRes("PRODUCT_007", true, "매니저 승인 성공.", null);
    }

    // ----------------------------------------------------------------------------------------------- //

    @Transactional
    public BaseResponse<ProductSchoolCreateResult> createSchool(ProductSchoolCreateReq productSchoolCreateReq) {
        Member ceo = ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (ceo.getAuthority().substring(5).equals("CEO")) {
            ProductSchool productSchool = ProductSchool.builder()
                    .ceoName(ceo.getMemberName())
                    .storeName(productSchoolCreateReq.getStoreName())
                    .businessNum(productSchoolCreateReq.getBusinessNum())
                    .productName(productSchoolCreateReq.getProductName())
                    .price(productSchoolCreateReq.getPrice())
                    .contents(productSchoolCreateReq.getContents())
                    .status(0)
                    .build();
            productSchoolRepository.save(productSchool);

            ProductSchoolCreateResult productSchoolCreateResult = ProductSchoolCreateResult.builder().build();
            productSchoolCreateResult.setIdx(productSchool.getIdx());

            return BaseResponse.successRes("PRODUCT_008", true, "업체가 등록되었습니다.", productSchoolCreateResult);
        } else {
            throw MemberAccountException.forInvalidAuthority();
        }
    }

    @Transactional
    public BaseResponse<List<ProductSchoolReadRes>> listSchool() {
        List<ProductSchool> result = productSchoolRepository.findByStatus(1);
        List<ProductSchoolReadRes> productSchoolReadResList = new ArrayList<>();

        for (ProductSchool productSchool : result) {
            List<ProductImage> productImages = productSchool.getProductImages();
            String filename = "";

            if (!productImages.isEmpty()) {
                ProductImage firstImage = productImages.get(0);
                filename = generatePresignedUrl(firstImage.getFilename(), firstImage.getFilename());
            }

            ProductSchoolReadRes productSchoolReadRes = ProductSchoolReadRes.builder()
                    .idx(productSchool.getIdx())
                    .storeName(productSchool.getStoreName())
                    .businessNum(productSchool.getBusinessNum())
                    .price(productSchool.getPrice())
                    .contents(productSchool.getContents())
                    .filename(filename)
                    .build();

            productSchoolReadResList.add(productSchoolReadRes);
        }

        return BaseResponse.successRes("PRODUCT_009", true, "업체 목록 조회 성공.", productSchoolReadResList);
    }

    @Transactional
    public BaseResponse<List<ProductSchoolReadRes>> adminListSchool() {
        List<ProductSchool> result = productSchoolRepository.findAll();
        List<ProductSchoolReadRes> productSchoolReadResList = new ArrayList<>();

        for (ProductSchool productSchool : result) {
            List<ProductImage> productImages = productSchool.getProductImages();
            String filename = "";

            if (!productImages.isEmpty()) {
                ProductImage firstImage = productImages.get(0);
                filename = generatePresignedUrl(firstImage.getFilename(), firstImage.getFilename());
            }

            ProductSchoolReadRes productSchoolReadRes = ProductSchoolReadRes.builder()
                    .idx(productSchool.getIdx())
                    .storeName(productSchool.getStoreName())
                    .businessNum(productSchool.getBusinessNum())
                    .price(productSchool.getPrice())
                    .contents(productSchool.getContents())
                    .filename(filename)
                    .build();

            productSchoolReadResList.add(productSchoolReadRes);
        }

        return BaseResponse.successRes("PRODUCT_010", true, "관리자용 업체 목록 조회 성공.", productSchoolReadResList);
    }

    @Transactional
    public BaseResponse<ProductSchoolReadRes> readSchool(Long idx) {
        ProductSchool productSchool = productSchoolRepository
                .findByIdx(idx).orElseThrow(() -> new RuntimeException("해당 idx의 상품을 찾을 수 없습니다."));

        ProductSchoolReadRes productSchoolReadRes = ProductSchoolReadRes.builder()
                .idx(productSchool.getIdx())
                .storeName(productSchool.getStoreName())
                .businessNum(productSchool.getBusinessNum())
                .price(productSchool.getPrice())
                .contents(productSchool.getContents())
                .build();

        return BaseResponse.successRes("PRODUCT_011", true, "업체 조회 성공.", productSchoolReadRes);
    }

    public BaseResponse<Void> updateSchool(ProductSchoolUpdateReq productSchoolUpdateReq) {
        ProductSchool productSchool = productSchoolRepository.findById(productSchoolUpdateReq.getIdx())
                .orElseThrow(() -> new RuntimeException("해당 idx의 상품을 찾을 수 없습니다."));

        productSchool.setProductName(productSchoolUpdateReq.getProductName());
        productSchool.setPrice(productSchoolUpdateReq.getPrice());
        productSchool.setBusinessNum(productSchoolUpdateReq.getBusinessNum());
        productSchool.setContents(productSchoolUpdateReq.getContents());

        productSchoolRepository.save(productSchool);

        return BaseResponse.successRes("PRODUCT_012", true, "업체 정보 수정 성공.", null);
    }

    @Transactional
    public BaseResponse<Void> deleteSchool(Long idx) {
        // 해당 idx의 ProductSchoolImage를 한 번에 삭제
        productImageRepository.deleteAllByProductSchoolIdx(idx);
        // ProductSchool의 status를 2로 변경
        ProductSchool productSchool = productSchoolRepository.findByIdx(idx).get();
        productSchool.setStatus(2);

        return BaseResponse.successRes("PRODUCT_013", true, "업체 삭제 성공.", null);
    }

    @Transactional
    public List<ProductFileDto> listFilesByProductSchoolIdx(Long productSchoolIdx) {
        // Board 엔티티의 ID를 기반으로 파일 목록 조회
        List<ProductImage> productImages = productImageRepository.findByProductSchoolIdx(productSchoolIdx);

        // 조회된 파일 목록을 BoardFileDto 리스트로 변환
        List<ProductFileDto> fileDtos = productImages.stream().map(file -> {
            // 각 파일에 대한 Presigned URL 생성
            String downloadUrl = generatePresignedUrl(file.getFilename(), file.getOriginalFilename());

            // 파일의 원본 이름과 다운로드 URL을 사용하여 GooutFileDto 객체 생성
            return new ProductFileDto(file.getIdx(), file.getOriginalFilename(), downloadUrl);
        }).collect(Collectors.toList());

        return fileDtos;
    }

    @Transactional
    public BaseResponse<Void> checkSchool(Long idx) {
        ProductSchool productSchool = productSchoolRepository.findByIdx(idx)
                .orElseThrow(() -> new RuntimeException("해당 idx의 업체 정보를 찾을 수 없습니다."));
        productSchool.setStatus(1);
        productSchoolRepository.save(productSchool);

        return BaseResponse.successRes("PRODUCT_014", true, "업체 승인 성공.", null);
    }

    // ----------------------------------------------------------------------------------------------- //

    @Transactional
    public String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        return folderPath;
    }

    @Transactional
    public String uploadFile(MultipartFile file, Long productIdx) {
        String originalName = file.getOriginalFilename();
        String folderPath = makeFolder();
        String uuid = UUID.randomUUID().toString();
        String saveFileName = folderPath + File.separator + uuid + "_" + originalName;
        InputStream input = null;
        try {
            input = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3.putObject(bucket, saveFileName.replace(File.separator, "/"), input, metadata);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return s3.getUrl(bucket, saveFileName.replace(File.separator, "/")).toString();
    }

    @Transactional
    public void saveFile(Long idx, String uploadPath) {
        productImageRepository.save(ProductImage.builder()
                .productSchool(ProductSchool.builder().idx(idx).build())
                .filename(uploadPath)
                .build());
    }

    @Transactional
    public String generatePresignedUrl(String fileKey, String fileName) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + 1000 * 60 * 10; // 10분 후 만료
        expiration.setTime(expTimeMillis);

        try {
            // 파일 이름을 URL 인코딩합니다.
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());

            // 파일 다운로드를 위한 Content-Disposition 설정
            String contentDisposition = String.format("attachment; filename*=UTF-8''%s", encodedFileName);

            // 응답 헤더를 설정합니다.
            ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides()
                    .withContentDisposition(contentDisposition);

            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileKey)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(expiration)
                    .withResponseHeaders(headerOverrides); // 응답 헤더를 포함시킵니다.

            URL url = s3.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        } catch (Exception e) {
            // 예외 처리
            throw new RuntimeException("URL 생성 중 오류 발생", e);
        }
    }

    @Transactional
    public void deleteFile(Long fileId) {
        productImageRepository.findById(fileId).ifPresent(file -> {
            // S3에서 파일 삭제
            s3.deleteObject(bucket, file.getFilename());
            // 데이터베이스에서 파일 정보 삭제
            productImageRepository.delete(file);
        });
    }
}