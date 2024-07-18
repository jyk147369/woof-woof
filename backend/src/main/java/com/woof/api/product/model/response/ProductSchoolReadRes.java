package com.woof.api.product.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProductSchoolReadRes {
    private Long idx;
    private String storeName;               // 업체 가게명
    private String businessNum;             // 업체 전화번호
    private String productName;             // 업체 상품명 (코스?)
    private Integer price;                  // 업체 가격
    private String contents;                // 상품 설명
    private String filename;                // list 메소드에서는 대표사진 한장
    private List<ProductFileDto> filenames; // read 메소드에서는 사진 여러장
    private Double averageRating;           // 평점
}
