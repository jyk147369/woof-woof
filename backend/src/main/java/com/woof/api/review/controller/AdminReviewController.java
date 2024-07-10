package com.woof.api.review.controller;

import com.woof.api.common.BaseResponse;
import com.woof.api.review.model.response.ReviewReadDto;
import com.woof.api.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/review")
@CrossOrigin("*")
public class AdminReviewController {
    private final ReviewService reviewService;

    public AdminReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/adminlist")
    public ResponseEntity<BaseResponse<List<ReviewReadDto>>> adminList(Long adminIdx) {
        BaseResponse<List<ReviewReadDto>> response = reviewService.myList(adminIdx);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/delete")
    public ResponseEntity<String> delete(@RequestParam Long idx) {
        reviewService.delete(idx);
        return ResponseEntity.ok().body("리뷰 idx : " + idx + "삭제 완료");
    }
}
