package com.woof.api.orders.model.dto;

import com.woof.api.member.model.entity.Member;
import lombok.*;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderDto {
    private String name;
    private String phoneNumber; //예약자 전화번호
    private Integer time; //예약시간
    private String place;//픽업 장소
    private String reservationStatus; //예약 상태
    private String orderDetails;
    private Integer status;
//    private String productName;

//    private ProductManager productManager;
//    private ProductCeo productCeo;
    private Member member;

    private Long productManagerIdx;
    private Long productCeoIdx;
    private Long memberIdx;

    private String impUid;




 }
