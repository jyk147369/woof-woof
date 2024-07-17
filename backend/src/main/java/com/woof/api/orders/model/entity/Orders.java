package com.woof.api.orders.model.entity;


import com.woof.api.common.BaseEntity;
import com.woof.api.member.model.entity.Member;
import com.woof.api.payment.model.Payment;
import com.woof.api.product.model.entity.ProductSchool;
import com.woof.api.product.model.entity.ProductManager;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Orders extends BaseEntity {

    private String name;
    private String phoneNumber; //예약자 전화번호
    private Integer time; //예약시간
    private String place;//픽업 장소
    private Integer reservationStatus; //예약 상태  0 (대기), 1 (확정), 2 (delete), 3 (완료)
    private String orderDetails; //세부 내용


//    @Column(nullable=false)
//    private String impUid;

//    @Column(nullable=false)
//    private LocalDate orderDate;


    //
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCeo_idx")
    private ProductSchool productSchool;

    //매니저 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productManager_idx")
    private ProductManager productManager;


    //고객 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    //결제 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_idx")
    private Payment payment;


    //리뷰 매핑 성공




}
