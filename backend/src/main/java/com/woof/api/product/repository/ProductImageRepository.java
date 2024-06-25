package com.woof.api.product.repository;

import com.woof.api.product.model.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    public List<ProductImage> findByProductSchoolIdx(Long idx);
    public List<ProductImage> findByProductManagerIdx(Long idx);
    public void deleteAllByProductSchoolIdx(Long idx);
    public void deleteAllByProductManagerIdx(Long idx);

}
