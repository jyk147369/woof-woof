package com.woof.api.productManager.repository;

import com.woof.api.productManager.model.ProductManager;
import com.woof.api.productManager.repository.querydsl.ProductManagerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductManagerRepository extends JpaRepository<ProductManager, Long> , ProductManagerRepositoryCustom {
    public Optional<ProductManager> findByManagerName(String managerName);

}