package com.woof.api.productManager.model.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ProductManagerReadRes2 {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private ProductManagerReadRes result;
    private Boolean success;
}