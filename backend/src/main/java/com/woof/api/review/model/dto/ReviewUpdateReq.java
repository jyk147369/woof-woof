package com.woof.api.review.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewUpdateReq {
    private Long idx;
    private String text;
    private Integer rating;
}
