package com.woof.api.review.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewReadDto {
    private Long idx;           // 기본키
    private String nickname;    // 작성자 닉네임
    private String text;        // 리뷰 내용
    private Integer rating;
    private Long orderIdx;      // 주문 idx
    private String filename;    //S3에 저장된 파일명 (UUID 포함)
}