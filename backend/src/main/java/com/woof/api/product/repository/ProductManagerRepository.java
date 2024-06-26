package com.woof.api.product.repository;

import com.woof.api.product.model.entity.ProductImage;
import com.woof.api.product.model.entity.ProductManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductManagerRepository extends JpaRepository<ProductManager, Long> {

    public List<ProductManager> findByStatus(Integer status);
    public Optional<ProductManager> findByIdx(Long idx);

}