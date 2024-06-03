package com.woof.api.product.model.dto.manager;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductManagerUpdateReq {
    private Long idx;
    private String managerName;     // 매니저 이름
    private String gender;          // 매니저 성별
    private String businessNum;     // 매니저 번호
    private Integer price;          // 매니저 1달 이용 가격
    private String career;          // 매니저 경력
    private String contents;        // 인사글
}