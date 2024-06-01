package com.woof.api.productCeo.repository.querydsl;

import com.querydsl.jpa.impl.JPAQuery;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productCeo.model.QProductCeo;
import com.woof.api.productCeo.model.QProductCeoImage;
import com.woof.api.productManager.model.ProductManager;
import com.woof.api.productManager.model.QProductManager;
import com.woof.api.productManager.model.QProductManagerImage;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
@Component
public class ProductCeoRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductCeoRepositoryCustom {


    public ProductCeoRepositoryCustomImpl() {
        super(ProductCeo.class);

    }

    @Override
    public List<ProductCeo> findList() {

        QProductCeo productCeo = new QProductCeo("productCeo");
        QProductCeoImage productCeoImage = new QProductCeoImage("productCeoImages");


        List<ProductCeo> result = from(productCeo)
                .leftJoin(productCeo.productCeoImages, productCeoImage).fetchJoin()
                .distinct().fetch();

        return result;
    }

    @PersistenceContext
    private EntityManager em;
    @Override
    public Optional<ProductCeo> findList2(Long idx) {

            QProductCeo productCeo = QProductCeo.productCeo;
            QProductCeoImage productCeoImage = QProductCeoImage.productCeoImage;

            JPAQuery<ProductCeo> query = new JPAQuery<>(em);
            ProductCeo result = query
                    .select(productCeo)
                    .from(productCeo)
                    .leftJoin(productCeo.productCeoImages, productCeoImage).fetchJoin()
                    .where(productCeo.idx.eq(idx))
                    .distinct()
                    .fetchOne();

            return Optional.ofNullable(result);


    }
}
