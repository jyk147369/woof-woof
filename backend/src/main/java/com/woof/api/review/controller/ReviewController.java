package com.woof.api.review.controller;


import com.woof.api.review.model.dto.ReviewDto;
import com.woof.api.review.model.dto.ReviewResDto;
import com.woof.api.review.service.ReviewNotFoundException;
import com.woof.api.review.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@CrossOrigin("*")
public class ReviewController {
    ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ApiOperation(value="리뷰 생성", notes="회원이 리뷰를 생성한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@RequestBody ReviewDto reviewDto) {
        ReviewResDto reviewResDto = reviewService.create(reviewDto);
        return ResponseEntity.ok().body(reviewResDto);
    }

    @ApiOperation(value="리뷰 조회", notes="회원이 리뷰를 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity read(Long idx) {
        return ResponseEntity.ok().body(reviewService.read(idx));
    }

    @ApiOperation(value="리뷰 수정", notes="회원이 리뷰 idx를 입력하여 특정 리뷰를 수정한다.")
    @PatchMapping("/update/{idx}")
    public ResponseEntity<String> update(@PathVariable Long idx, @RequestBody ReviewDto reviewDto) {
        try {
            reviewService.update(idx, reviewDto);
            return ResponseEntity.ok().body("리뷰가 성공적으로 업데이트되었습니다.");
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ApiOperation(value="리뷰 삭제", notes="회원이 리뷰를 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long idx) {
        reviewService.delete(idx);
        return ResponseEntity.ok().body("리뷰가 삭제되었습니다.");
    }
}