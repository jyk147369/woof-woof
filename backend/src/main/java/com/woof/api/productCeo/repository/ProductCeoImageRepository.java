package com.woof.api.productCeo.repository;

import com.woof.api.productCeo.model.ProductCeoImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCeoImageRepository extends JpaRepository<ProductCeoImage, Long> {

    List<ProductCeoImage> findAllByProductCeoIdx(Long idx);

    /**
     * Select * from productceoimage
     * where productceoid = idx;
     */
}
