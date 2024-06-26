package com.woof.api.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewListRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private List<ReviewReadDto> result;
    private Boolean success;
}
