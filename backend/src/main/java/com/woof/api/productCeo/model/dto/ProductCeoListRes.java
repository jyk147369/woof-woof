package com.woof.api.productCeo.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProductCeoListRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private List<ProductCeoReadRes> result;
    private Boolean success;
}