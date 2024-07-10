package com.woof.api.product.model.request;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductSchoolCreateReq {
    private String storeName;
    private String businessNum;
    private String productName;
    private Integer phoneNumber;
    private Integer price;
    private String contents;
}
