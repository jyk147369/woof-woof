package com.woof.api.review.model;


import com.woof.api.orders.model.Orders;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productManager.model.ProductManager;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;
    private String text;
//    private Integer productNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCeo_idx")
    ProductCeo productCeo; //상품 : 리뷰 = 1 : N

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productManagerIdx")
    ProductManager productManager; //상품 : 리뷰 = 1 : N

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordersIdx")
    Orders orders; //주문 : 리뷰 = 1 : N

    @Builder
    public Review(Long idx, String name, String text, ProductCeo productCeo, Integer productNumber, ProductManager productManager, Orders orders ){
        this.idx = idx;
        this.name = name;
        this.text = text;
        this.productCeo = productCeo;
        this.productManager = productManager;
//        this.productNumber = productNumber;
        this.orders = orders;
    }


}