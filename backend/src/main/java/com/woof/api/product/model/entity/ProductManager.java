package com.woof.api.product.model.entity;

import com.woof.api.bookmark.model.Bookmark;
import com.woof.api.common.BaseEntity;
import com.woof.api.orders.model.entity.Orders;
import com.woof.api.review.model.entity.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@SuperBuilder
public class ProductManager extends BaseEntity {
    private String managerName;     // 매니저 이름
    private Long managerIdx;
    private String gender;          // 매니저 성별
    private String businessNum;     // 매니저 번호
    private Integer price;          // 매니저 1달 이용 가격
    private String career;          // 매니저 경력
    private String contents;        // 인사글
    private Integer status;         // 등록시 0, 관리자가 승인시 1, 삭제 요청시 2로 변경후 1년 뒤 삭제

    @OneToMany(mappedBy = "productManager")
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "productManager")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "productManager")
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "productManager")
    private List<Review> reviews = new ArrayList<>();
}
