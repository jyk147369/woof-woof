package com.woof.api.productManager.repository;

import com.woof.api.productManager.model.ProductManagerImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductManagerImageRepository extends JpaRepository<ProductManagerImage, Long> {
    List<ProductManagerImage> findAllByProductManagerIdx(Long idx);

    /**
     * Select * from productceoimage
     * where productceoid = idx;
     */
}