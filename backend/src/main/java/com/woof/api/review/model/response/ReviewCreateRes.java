package com.woof.api.review.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewCreateRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private String result;
    private Boolean success;
}