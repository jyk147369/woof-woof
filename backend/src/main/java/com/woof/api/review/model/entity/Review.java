package com.woof.api.review.model.entity;


import com.woof.api.common.BaseEntity;
import com.woof.api.orders.model.Orders;
import com.woof.api.product.model.entity.ProductImage;
import com.woof.api.product.model.entity.ProductSchool;
import com.woof.api.product.model.entity.ProductManager;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;           // 기본키
    private String nickname;    // 작성자 닉네임
    private String text;        // 리뷰 내용
    private Long orderIdx;      // 주문 idx
    private Integer status;     // 등록시 1, 삭제 요청시 2로 변경후 1년 뒤 삭제
    private Integer rating;     // 리뷰 평점 (프론트에서 1~5만 주게 할거임)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productSchool_idx")
    ProductSchool productSchool; //상품 : 리뷰 = 1 : N

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productManager_idx")
    ProductManager productManager; //상품 : 리뷰 = 1 : N

    @OneToMany(mappedBy = "review")
    private List<ReviewImage> reviewImages = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "orders_idx")
//    Orders orders; //주문 : 리뷰 = 1 : N
}