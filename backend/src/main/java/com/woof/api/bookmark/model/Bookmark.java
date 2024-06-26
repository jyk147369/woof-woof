package com.woof.api.bookmark.model;


import com.woof.api.member.model.entity.Member;
import com.woof.api.product.model.entity.ProductSchool;
import com.woof.api.product.model.entity.ProductManager;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 즐겨찾기 : 사용자 = N : 1
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_idx")
    private Member member;

    // 즐겨찾기 : 업체 = N : 1
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productCeo_idx")
    private ProductSchool productSchool;

    // 즐겨찾기 : 매니저 = N : 1
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productManager_idx")
    private ProductManager productManager;

}
