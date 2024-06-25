package com.woof.api.product.model.entity;


import com.woof.api.common.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@SuperBuilder
public class ProductImage extends BaseEntity {
    private String filename; // S3에 저장된 파일명 (UUID 포함)
    private String originalFilename; // 원본 파일 이름을 저장할 필드 추가

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productSchool_idx")
    private ProductSchool productSchool;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productManager_idx")
    private ProductManager productManager;
}
