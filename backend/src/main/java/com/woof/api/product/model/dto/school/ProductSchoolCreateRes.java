package com.woof.api.product.model.dto.school;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductSchoolCreateRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private String result;
    private Boolean success;
}
