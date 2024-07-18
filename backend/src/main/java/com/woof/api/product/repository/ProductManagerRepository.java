package com.woof.api.product.repository;

import com.woof.api.product.model.entity.ProductImage;
import com.woof.api.product.model.entity.ProductManager;
import com.woof.api.review.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductManagerRepository extends JpaRepository<ProductManager, Long> {

    public List<ProductManager> findByStatus(Integer status);
    public ProductManager findByIdx(Long idx);
    List<ProductManager> findByStatusAndUpdateAtBefore(int status, LocalDateTime dateTime);
}