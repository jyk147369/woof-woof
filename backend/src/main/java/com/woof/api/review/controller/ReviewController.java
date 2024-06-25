package com.woof.api.review.controller;


import com.woof.api.common.BaseResponse;
import com.woof.api.review.model.request.ReviewCreateReq;
import com.woof.api.review.model.request.ReviewFileDto;
import com.woof.api.review.model.request.ReviewUpdateReq;
import com.woof.api.review.model.response.ReviewCreateResult;
import com.woof.api.review.model.response.ReviewListRes;
import com.woof.api.review.model.response.ReviewReadDto;
import com.woof.api.review.service.ReviewService;
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

    //    @ApiOperation(value="리뷰 생성", notes="회원이 리뷰를 생성한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<BaseResponse<ReviewCreateResult>> createReview(
            @RequestPart ReviewCreateReq reviewCreateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {


        BaseResponse<ReviewCreateResult> baseResponse = reviewService.create(reviewCreateReq);
        ReviewCreateResult reviewCreateResult = baseResponse.getResult();

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = reviewService.uploadFile(uploadFile, reviewCreateResult.getIdx());
                reviewService.saveFile(reviewCreateResult.getIdx(), uploadPath);
            }
        }

        return ResponseEntity.ok().body(baseResponse);
    }

    //    @ApiOperation(value="매니저별 리뷰 목록 조회", notes="회원이 매니저별로 리뷰를 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/manager/list")
    public ResponseEntity<BaseResponse<List<ReviewReadDto>>> listByManager(Long managerIdx) {
        BaseResponse<List<ReviewReadDto>> response = reviewService.listByManager(managerIdx);
        return ResponseEntity.ok().body(response);
    }
    //    @ApiOperation(value="유치원별 리뷰 목록 조회", notes="회원이 유치원별로 리뷰를 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/school/list")
    public ResponseEntity<BaseResponse<List<ReviewReadDto>>> listBySchool(Long schoolIdx) {
        BaseResponse<List<ReviewReadDto>> response = reviewService.listBySchool(schoolIdx);
        return ResponseEntity.ok().body(response);
    }

    //    @ApiOperation(value="내 리뷰 목록 조회", notes="회원이 자신이 작성한 리뷰를 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/mylist")
    public ResponseEntity<BaseResponse<List<ReviewReadDto>>> myList(Long memberIdx) {
        BaseResponse<List<ReviewReadDto>> response = reviewService.myList(memberIdx);
        return ResponseEntity.ok().body(response);
    }

    //    @ApiOperation(value="관리자 리뷰 목록 조회", notes="관리자가 작성한 리뷰를 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/adminlist")
    public ResponseEntity<BaseResponse<List<ReviewReadDto>>> adminList(Long adminIdx) {
        BaseResponse<List<ReviewReadDto>> response = reviewService.myList(adminIdx);
        return ResponseEntity.ok().body(response);
    }

    //    @ApiOperation(value="리뷰 수정", notes="회원이 작성한 리뷰를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity<BaseResponse<Void>> update(@RequestPart ReviewUpdateReq reviewUpdateReq,
                                         @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {
        BaseResponse<Void> response = reviewService.update(reviewUpdateReq);

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = reviewService.uploadFile(uploadFile, reviewUpdateReq.getIdx());
                reviewService.saveFile(reviewUpdateReq.getIdx(), uploadPath);
            }
        }
        return ResponseEntity.ok(response);
    }

    //    @ApiOperation(value="리뷰 삭제", notes="회원이 작성한 리뷰를 삭제한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/delete")
    public ResponseEntity<BaseResponse<Void>>  delete(@RequestParam Long idx) {
        BaseResponse<Void> response = reviewService.delete(idx);
        reviewService.delete(idx);

        return ResponseEntity.ok().body(response);
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