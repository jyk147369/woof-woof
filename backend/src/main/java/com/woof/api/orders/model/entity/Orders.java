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
    private String reservation_status; //예약 상태
    private String orderDetails;
    private Integer price;

    @Column(nullable=false)
    private String impUid;

    @Column(nullable=false)
    private LocalDate orderDate;


    //
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCeo_idx")
    private ProductSchool productSchool;

    //매니저 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productManager_idx")
    private ProductManager productManager;

    //업체 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ceo_idx")
    private Member ceo;

    //고객 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    //결제 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_idx")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_idx")
    private Member manager;

    //리뷰 매핑 성공




}
