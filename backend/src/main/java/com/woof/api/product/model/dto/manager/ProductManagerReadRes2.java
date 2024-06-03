package com.woof.api.product.model.dto.manager;

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