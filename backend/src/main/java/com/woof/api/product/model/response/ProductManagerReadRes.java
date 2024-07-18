package com.woof.api.product.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProductManagerReadRes {
    private Long idx;
    private String managerName;             // 매니저 이름
    private String gender;                  // 매니저 성별
    private String businessNum;             // 매니저 번호
    private Integer price;                  // 매니저 1달 이용 가격
    private String career;                  // 매니저 경력
    private String contents;                // 매니저 설명
    private String filename;                // list 메소드에서는 대표사진 한장
    private List<ProductFileDto> filenames; // read 메소드에서는 사진 여러장
    private Double averageRating;           // 평점
}
