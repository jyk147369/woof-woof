package com.woof.api.review.controller;

//import com.woof.api.review.model.dto.ReviewListRes;
import com.woof.api.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/review")
@CrossOrigin("*")
public class AdminReviewController {
    private final ReviewService reviewService;

    public AdminReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/list")
//    public ResponseEntity<ReviewListRes> list() {
//        return ResponseEntity.ok().body(reviewService.adminList());
//    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/delete")
    public ResponseEntity<String> delete(@RequestParam Long idx) {
        reviewService.delete(idx);
        return ResponseEntity.ok().body("리뷰 idx : " + idx + "삭제 완료");
    }
}
