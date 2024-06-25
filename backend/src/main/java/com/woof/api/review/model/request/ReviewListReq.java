package com.woof.api.review.model.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewListReq {
    private Long idx;           // 기본키
    private String nickname;    // 작성자 닉네임
    private String text;        // 리뷰 내용
    private Integer rating;
    private Long orderIdx;      // 주문 idx
}
