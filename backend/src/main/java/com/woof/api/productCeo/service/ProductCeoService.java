package com.woof.api.productCeo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.woof.api.member.model.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productCeo.model.ProductCeoImage;

import com.woof.api.productCeo.model.QProductCeoImage;
import com.woof.api.productCeo.model.dto.ProductCeoCreateReq;
import com.woof.api.productCeo.model.dto.ProductCeoListRes;
import com.woof.api.productCeo.model.dto.ProductCeoReadRes;
import com.woof.api.productCeo.model.dto.ProductCeoReadRes2;
import com.woof.api.productCeo.model.dto.ProductCeoUpdateReq;
import com.woof.api.productCeo.repository.ProductCeoImageRepository;
import com.woof.api.productCeo.repository.ProductCeoRepository;
import com.woof.api.productCeo.repository.querydsl.ProductCeoRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCeoService {
    private final ProductCeoRepository productCeoRepository;
    private final ProductCeoImageRepository productCeoImageRepository;
    private final ProductCeoRepositoryCustomImpl productCeoRepositoryCustom;
    private final AmazonS3 s3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public ProductCeo createCeo(ProductCeoCreateReq productCeoCreateReq) {

        return productCeoRepository.save(ProductCeo.builder()
                .storeName(productCeoCreateReq.getStoreName())
                .productName(productCeoCreateReq.getProductName())
                .phoneNumber(productCeoCreateReq.getPhoneNumber())
                .price(productCeoCreateReq.getPrice())
                .contents(productCeoCreateReq.getContents())
                .build());
    }

    @Transactional
    public ProductCeoListRes listCeo() {
        List<ProductCeo> result = productCeoRepositoryCustom.findList();

//        List<ProductCeoImage> all = productCeoImageRepository.findAll();

        List<ProductCeoReadRes> productCeoReadResList = new ArrayList<>();


        for (ProductCeo productCeo : result) {
            List<ProductCeoImage> productCeoImages = productCeo.getProductCeoImages();

            String filenames = "";
            for (ProductCeoImage productCeoImage : productCeoImages) {
                String filename = productCeoImage.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);


            ProductCeoReadRes productCeoReadRes = ProductCeoReadRes.builder()
                    .idx(productCeo.getIdx())
                    .productName(productCeo.getProductName())
                    .storeName(productCeo.getStoreName())
                    .phoneNumber(productCeo.getPhoneNumber())
                    .price(productCeo.getPrice())
                    .contents(productCeo.getContents())
                    .filename(filenames)
                    .build();

            productCeoReadResList.add(productCeoReadRes);
        }


        return ProductCeoListRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(productCeoReadResList)
                .build();
    }
    @Transactional
    public ProductCeoReadRes2 readCeo(Long idx) {
        Optional<ProductCeo> resultCeo = productCeoRepositoryCustom.findList2(idx);

        if (resultCeo.isPresent()) {
            ProductCeo productCeo = resultCeo.get();

            List<ProductCeoImage> productCeoImages = productCeo.getProductCeoImages();

            String filenames = "";
            for (ProductCeoImage productCeoImage : productCeoImages) {
                String filename = productCeoImage.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);


            ProductCeoReadRes productCeoReadRes = ProductCeoReadRes.builder()
                    .idx(productCeo.getIdx())
                    .productName(productCeo.getProductName())
                    .storeName(productCeo.getStoreName())
                    .phoneNumber(productCeo.getPhoneNumber())
                    .price(productCeo.getPrice())
                    .contents(productCeo.getContents())
                    .filename(filenames)
                    .build();

            return ProductCeoReadRes2.builder()
                    .code(1000)
                    .message("요청 성공.")
                    .success(true)
                    .isSuccess(true)
                    .result(productCeoReadRes)
                    .build();
        }
        return null;
    }

    public void updateCeo(ProductCeoUpdateReq productCeoUpdateReq) {
        Optional<ProductCeo> result = productCeoRepository.findById(productCeoUpdateReq.getIdx());
        if (result.isPresent()) {
            ProductCeo productCeo = result.get();
            productCeo.setStoreName(productCeoUpdateReq.getStoreName());
            productCeo.setProductName(productCeoUpdateReq.getProductName());
            productCeo.setPrice(productCeoUpdateReq.getPrice());
            productCeo.setPhoneNumber(productCeoUpdateReq.getPhoneNumber());
            productCeo.setContents(productCeoUpdateReq.getContents());

            productCeoRepository.save(productCeo);
        }
    }

    @PersistenceContext
    private EntityManager em;
    @Transactional
    public void deleteCeo(Long idx) {
        // ProductCeoImage를 한 번에 삭제
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProductCeoImage qProductCeoImage = QProductCeoImage.productCeoImage;
        queryFactory.delete(qProductCeoImage).where(qProductCeoImage.productCeo.idx.eq(idx)).execute();

        // ProductCeo 삭제
        productCeoRepository.delete(ProductCeo.builder().idx(idx).build());
    }

    public String makeFolderCeo() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        return folderPath;
    }


    public String uploadFileCeo(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String folderPath = makeFolderCeo();
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

    public void saveFileCeo(Long idx, String uploadPath) {
        productCeoImageRepository.save(ProductCeoImage.builder()
                .productCeo(ProductCeo.builder().idx(idx).build())
                .filename(uploadPath)
                .build());
    }

}