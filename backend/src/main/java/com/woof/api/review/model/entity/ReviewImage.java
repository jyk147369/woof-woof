package com.woof.api.review.model.entity;

import com.woof.api.common.BaseEntity;
import com.woof.api.product.model.entity.ProductManager;
import com.woof.api.product.model.entity.ProductSchool;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class ReviewImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String filename; // S3에 저장된 파일명 (UUID 포함)
    private String originalFilename; // 원본 파일 이름을 저장할 필드 추가

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_idx")
    private Review review;
}
