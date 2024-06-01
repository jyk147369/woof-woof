package com.woof.api.productManager.model.dto;

import com.woof.api.productManager.model.dto.ProductManagerCreateResult;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductManagerCreateRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private ProductManagerCreateResult result;
    private Boolean success;
}
