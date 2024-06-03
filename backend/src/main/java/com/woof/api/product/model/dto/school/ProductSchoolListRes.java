package com.woof.api.product.model.dto.school;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProductSchoolListRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private List<ProductSchoolReadRes> result;
    private Boolean success;
}