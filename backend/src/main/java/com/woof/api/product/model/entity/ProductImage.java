package com.woof.api.product.model.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String filename; // S3에 저장된 파일명 (UUID 포함)
    private String originalFilename; // 원본 파일 이름을 저장할 필드 추가

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productSchool_idx")
    private ProductSchool productSchool;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productManager_idx")
    private ProductManager productManager;
}
