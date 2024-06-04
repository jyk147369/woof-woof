package com.woof.api.review.controller;


import com.woof.api.product.model.dto.ProductFileDto;
import com.woof.api.product.model.dto.manager.ProductManagerListRes;
import com.woof.api.product.model.dto.manager.ProductManagerUpdateReq;
import com.woof.api.review.model.dto.*;
import com.woof.api.review.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/review")
@CrossOrigin("*")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<ReviewCreateRes> createReview(
            @RequestPart ReviewCreateReq reviewCreateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {
        ReviewCreateResult reviewCreateResult = reviewService.create(reviewCreateReq);

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = reviewService.uploadFile(uploadFile, reviewCreateResult.getIdx());
                reviewService.saveFile(reviewCreateResult.getIdx(), uploadPath);
            }
        }

        ReviewCreateRes response = ReviewCreateRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result("리뷰 idx : " + reviewCreateResult.getIdx())
                .build();
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/manager/list")
    public ResponseEntity<ReviewListRes> listByManager(Long managerIdx) {
        return ResponseEntity.ok().body(reviewService.listByManager(managerIdx));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/school/list")
    public ResponseEntity<ReviewListRes> listBySchool(Long schoolIdx) {
        return ResponseEntity.ok().body(reviewService.listBySchool(schoolIdx));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mylist")
    public ResponseEntity<ReviewListRes> myList(Long memberIdx) {
        return ResponseEntity.ok().body(reviewService.myList(memberIdx));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity<String> update(@RequestPart ReviewUpdateReq reviewUpdateReq,
                                         @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {
        reviewService.update(reviewUpdateReq);

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = reviewService.uploadFile(uploadFile, reviewUpdateReq.getIdx());
                reviewService.saveFile(reviewUpdateReq.getIdx(), uploadPath);
            }
        }
        return ResponseEntity.ok("수정 완료");
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/delete")
    public ResponseEntity<String> delete(@RequestParam Long idx) {
        reviewService.delete(idx);
        return ResponseEntity.ok().body("리뷰 idx : " + idx + "삭제 완료");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/files/{reviewIdx}")
    public ResponseEntity<List<ReviewFileDto>> listFilesByReviewIdx(@PathVariable Long reviewIdx) {
        List<ReviewFileDto> files = reviewService.listFilesByReviewIdx(reviewIdx);
        return ResponseEntity.ok().body(files);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam Long fileId) {
        reviewService.deleteFile(fileId);
        return ResponseEntity.ok("파일 id " + fileId + " 삭제 완료");
    }
}