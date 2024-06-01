package com.woof.api.productCeo.model.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ProductCeoReadRes2 {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private ProductCeoReadRes result;
    private Boolean success;
}