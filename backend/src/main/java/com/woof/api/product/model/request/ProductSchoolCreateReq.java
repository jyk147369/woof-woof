package com.woof.api.product.model.request;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductSchoolCreateReq {
    private String storeName;
//    private String businessNum; // 매니저 번호 > member에서 가져오기 때문에 주석처리
    private String productName;
//    private Integer phoneNumber; // 매니저 번호 > member에서 가져오기 때문에 주석처리
    private Integer price;
    private String contents;
}