package com.woof.api.product.model.response;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ProductSchoolReadRes2 {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private ProductSchoolReadRes result;
    private Boolean success;
}