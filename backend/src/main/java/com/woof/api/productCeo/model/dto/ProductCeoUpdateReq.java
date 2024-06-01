package com.woof.api.productCeo.model.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductCeoUpdateReq {
    private Long idx;
    private String storeName;
    private String productName;
    private Integer phoneNumber;
    private Integer price;
    private String contents;
}