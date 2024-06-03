package com.woof.api.product.model.dto.school;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductSchoolUpdateReq {
    private Long idx;
    private String productName;
    private String businessNum;
    private Integer price;
    private String contents;
}