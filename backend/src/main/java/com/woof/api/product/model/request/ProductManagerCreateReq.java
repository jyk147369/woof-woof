package com.woof.api.product.model.request;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductManagerCreateReq {
//    private String managerName;     // 매니저 이름 > member에서 가져오기 때문에 주석처리
    private String gender;          // 매니저 성별
//    private String businessNum;     // 매니저 번호 > member에서 가져오기 때문에 주석처리
    private Integer price;          // 매니저 1달 이용 가격
    private String career;          // 매니저 경력
    private String contents;        // 인사글
}
