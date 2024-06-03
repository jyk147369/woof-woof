package com.woof.api.product.model.dto.school;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductSchoolReadRes {
    private Long idx;
    private String storeName;
    private String businessNum;
    private String productName;
    private Integer price;
    private String contents;
}
