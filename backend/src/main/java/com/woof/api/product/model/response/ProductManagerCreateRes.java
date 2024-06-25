package com.woof.api.product.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductManagerCreateRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private String result;
    private Boolean success;
}
