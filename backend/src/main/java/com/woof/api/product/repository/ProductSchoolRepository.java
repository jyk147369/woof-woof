package com.woof.api.product.repository;

import com.woof.api.product.model.entity.ProductManager;
import com.woof.api.product.model.entity.ProductSchool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductSchoolRepository extends JpaRepository<ProductSchool, Long> {

    public List<ProductSchool> findByStatus(Integer status);
    public ProductSchool findByIdx(Long idx);
    List<ProductSchool> findByStatusAndUpdateAtBefore(int status, LocalDateTime dateTime);
}