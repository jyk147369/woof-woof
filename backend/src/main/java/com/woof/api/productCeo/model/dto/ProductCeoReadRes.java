package com.woof.api.productCeo.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductCeoReadRes {
    private Long idx;
    private String productName;
    private String storeName;
    private Integer phoneNumber;
    private Integer price;
    private String filename;
    private String contents;
}
