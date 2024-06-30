package com.woof.api.review.model.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewCreateReq {
    private String text;        // 리뷰 내용
    private Integer rating;
    private Long orderIdx;      // 주문 idx
}








