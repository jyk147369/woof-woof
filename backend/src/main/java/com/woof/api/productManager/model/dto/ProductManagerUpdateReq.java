package com.woof.api.productManager.model.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductManagerUpdateReq {
    private Long idx;
    private String managerName;
    private String gender;
    private Integer phoneNumber;
    private Integer price;
    private String contents;
}