package com.woof.api.review.service;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.product.model.dto.school.ProductSchoolListRes;
import com.woof.api.product.model.entity.ProductSchool;
import com.woof.api.product.repository.ProductManagerRepository;
import com.woof.api.product.repository.ProductSchoolRepository;
import com.woof.api.review.model.dto.*;
import com.woof.api.review.model.entity.Review;
import com.woof.api.review.model.entity.ReviewImage;
import com.woof.api.review.repository.ReviewImageRepository;
import com.woof.api.review.repository.ReviewRepository;
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
public class ReviewService {
    ReviewRepository reviewRepository;
    ReviewImageRepository reviewImageRepository;

    private final AmazonS3 s3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public ReviewCreateResult create(ReviewCreateReq reviewCreateReq) {
        Member member = ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Review review = Review.builder()
                .nickname(member.getNickname())
                .text(reviewCreateReq.getText())
                .rating(reviewCreateReq.getRating())
                .orderIdx(reviewCreateReq.getOrderIdx())
                .status(1)
                .build();
        reviewRepository.save(review);

        ReviewCreateResult reviewCreateResult = ReviewCreateResult.builder().build();
        reviewCreateResult.setIdx(review.getIdx());

        return reviewCreateResult;
    }

    @Transactional
    public ReviewListRes listByManager(Long managerIdx) {
        List<Review> result = reviewRepository.findByProductManagerIdxAndStatus(managerIdx, 1);
        List<ReviewReadDto> reviewReadDtos = new ArrayList<>();

        for (Review review : result) {
            List<ReviewImage> reviewImages = review.getReviewImages();

            String filenames = "";
            for (ReviewImage reviewImage : reviewImages) {
                String filename = reviewImage.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);

            ReviewReadDto reviewReadDto = ReviewReadDto.builder()
                    .idx(review.getIdx())
                    .nickname(review.getNickname())
                    .text(review.getText())
                    .rating(review.getRating())
                    .orderIdx(review.getOrderIdx())
                    .filename(filenames)
                    .build();

            reviewReadDtos.add(reviewReadDto);
        }

        return ReviewListRes.builder()
                .code(1000)
                .message("매니저 리뷰 조회 성공.")
                .success(true)
                .isSuccess(true)
                .result(reviewReadDtos)
                .build();
    }

    @Transactional
    public ReviewListRes listBySchool(Long schoolIdx) {
        List<Review> result = reviewRepository.findByProductSchoolIdxAndStatus(schoolIdx, 1);
        List<ReviewReadDto> reviewReadDtos = new ArrayList<>();

        for (Review review : result) {
            List<ReviewImage> reviewImages = review.getReviewImages();

            String filenames = "";
            for (ReviewImage reviewImage : reviewImages) {
                String filename = reviewImage.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);

            ReviewReadDto reviewReadDto = ReviewReadDto.builder()
                    .idx(review.getIdx())
                    .nickname(review.getNickname())
                    .text(review.getText())
                    .rating(review.getRating())
                    .orderIdx(review.getOrderIdx())
                    .filename(filenames)
                    .build();

            reviewReadDtos.add(reviewReadDto);
        }

        return ReviewListRes.builder()
                .code(1000)
                .message("업체 리뷰 조회 성공.")
                .success(true)
                .isSuccess(true)
                .result(reviewReadDtos)
                .build();
    }

    @Transactional
    public ReviewListRes myList(Long memberIdx) {
        List<Review> result = reviewRepository.findByMemberIdx(memberIdx);
        List<ReviewReadDto> reviewReadDtos = new ArrayList<>();

        for (Review review : result) {
            List<ReviewImage> reviewImages = review.getReviewImages();

            String filenames = "";
            for (ReviewImage reviewImage : reviewImages) {
                String filename = reviewImage.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);

            ReviewReadDto reviewReadDto = ReviewReadDto.builder()
                    .idx(review.getIdx())
                    .nickname(review.getNickname())
                    .text(review.getText())
                    .rating(review.getRating())
                    .orderIdx(review.getOrderIdx())
                    .filename(filenames)
                    .build();

            reviewReadDtos.add(reviewReadDto);
        }

        return ReviewListRes.builder()
                .code(1000)
                .message("업체 리뷰 조회 성공.")
                .success(true)
                .isSuccess(true)
                .result(reviewReadDtos)
                .build();
    }

    @Transactional
    public ReviewListRes adminList() {
        List<Review> result = reviewRepository.findAll();
        List<ReviewReadDto> reviewReadDtos = new ArrayList<>();

        for (Review review : result) {
            List<ReviewImage> reviewImages = review.getReviewImages();

            String filenames = "";
            for (ReviewImage reviewImage : reviewImages) {
                String filename = reviewImage.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);

            ReviewReadDto reviewReadDto = ReviewReadDto.builder()
                    .idx(review.getIdx())
                    .nickname(review.getNickname())
                    .text(review.getText())
                    .rating(review.getRating())
                    .orderIdx(review.getOrderIdx())
                    .filename(filenames)
                    .build();

            reviewReadDtos.add(reviewReadDto);
        }

        return ReviewListRes.builder()
                .code(1000)
                .message("업체 리뷰 조회 성공.")
                .success(true)
                .isSuccess(true)
                .result(reviewReadDtos)
                .build();
    }

    public void update(ReviewUpdateReq reviewUpdateReq) {
        Review review = reviewRepository.findByIdx(reviewUpdateReq.getIdx())
                .orElseThrow(() -> new RuntimeException("해당 idx의 리뷰를 찾을 수 없습니다."));

        review.setText(reviewUpdateReq.getText());
        review.setRating(reviewUpdateReq.getRating());
        reviewRepository.save(review);
    }

    public void delete(Long idx) {
        // 해당 idx의 ReviewImage를 한 번에 삭제
        reviewImageRepository.deleteAllByReviewIdx(idx);
        // ProductSchool의 status를 2로 변경
        Review review = reviewRepository.findByIdx(idx).get();
        review.setStatus(2);
    }

// ----------------------------------------------------------------------------------------------- //

    @Transactional
    public String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        return folderPath;
    }

    @Transactional
    public String uploadFile(MultipartFile file, Long reviewIdx) {
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
        reviewImageRepository.save(ReviewImage.builder()
                .review(Review.builder().idx(idx).build())
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
    public List<ReviewFileDto> listFilesByReviewIdx(Long reviewIdx) {
        // Board 엔티티의 ID를 기반으로 파일 목록 조회
        List<ReviewImage> reviewImages = reviewImageRepository.findByReviewIdx(reviewIdx);

        // 조회된 파일 목록을 BoardFileDto 리스트로 변환
        List<ReviewFileDto> fileDtos = reviewImages.stream().map(file -> {
            // 각 파일에 대한 Presigned URL 생성
            String downloadUrl = generatePresignedUrl(file.getFilename(), file.getOriginalFilename());

            // 파일의 원본 이름과 다운로드 URL을 사용하여 GooutFileDto 객체 생성
            return new ReviewFileDto(file.getIdx(), file.getOriginalFilename(), downloadUrl);
        }).collect(Collectors.toList());

        return fileDtos;
    }

    @Transactional
    public void deleteFile(Long fileId) {
        reviewImageRepository.findById(fileId).ifPresent(file -> {
            // S3에서 파일 삭제
            s3.deleteObject(bucket, file.getFilename());
            // 데이터베이스에서 파일 정보 삭제
            reviewImageRepository.delete(file);
        });
    }
}