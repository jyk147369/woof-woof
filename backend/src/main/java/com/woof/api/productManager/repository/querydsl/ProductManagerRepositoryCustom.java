package com.woof.api.productManager.repository.querydsl;

import com.woof.api.productManager.model.ProductManager;
import com.woof.api.productManager.model.ProductManagerImage;
import com.woof.api.productManager.repository.ProductManagerRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductManagerRepositoryCustom {
    public List<ProductManager> findList();

    public Optional<ProductManager> findList2(Long idx);
}
