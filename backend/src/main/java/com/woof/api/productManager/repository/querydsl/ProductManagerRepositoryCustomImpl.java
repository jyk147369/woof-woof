package com.woof.api.productManager.repository.querydsl;

import com.querydsl.jpa.impl.JPAQuery;
import com.woof.api.productManager.model.ProductManager;

import com.woof.api.productManager.model.QProductManager;
import com.woof.api.productManager.model.QProductManagerImage;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class ProductManagerRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductManagerRepositoryCustom {


    public ProductManagerRepositoryCustomImpl() {
        super(ProductManager.class);
    }

    @Override
    public List<ProductManager> findList() {


        QProductManager productManager = new QProductManager("productManager");
        QProductManagerImage productManagerImage = new QProductManagerImage("productManagerImage");


        List<ProductManager> result = from(productManager)
                .leftJoin(productManager.productManagerImages, productManagerImage).fetchJoin()
                .distinct().fetch();



        return result;
    }

    @PersistenceContext
    private EntityManager em;
    @Override
    public Optional<ProductManager> findList2(Long idx) {
        QProductManager productManager = QProductManager.productManager;
        QProductManagerImage productManagerImage = QProductManagerImage.productManagerImage;

        JPAQuery<ProductManager> query = new JPAQuery<>(em);
        ProductManager result = query
                .select(productManager)
                .from(productManager)
                .leftJoin(productManager.productManagerImages, productManagerImage).fetchJoin()
                .where(productManager.idx.eq(idx))
                .distinct()
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
