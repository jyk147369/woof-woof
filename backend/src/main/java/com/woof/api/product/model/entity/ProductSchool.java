package com.woof.api.product.model.entity;

import com.woof.api.cart.model.Cart;
import com.woof.api.orders.model.Orders;
import com.woof.api.review.model.Review;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class ProductSchool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String storeName;       // 유치원 이름
    private String businessNum;     // 유치원 번호
    private String productName;     // 코스 이름
    private Integer price;          // 코스 가격
    private String contents;        // 코스 설명
    private Integer status;         // 등록시 0, 관리자가 승인시 1, 삭제 요청시 2로 변경후 1년 뒤 삭제

    @OneToMany(mappedBy = "productSchool")
    private List<Orders> orders = new ArrayList<>();
//    합칠 때 활성화

    @OneToMany(mappedBy = "productSchool")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "productSchool")
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "productSchool")
    private List<Review> reviews = new ArrayList<>();
}
