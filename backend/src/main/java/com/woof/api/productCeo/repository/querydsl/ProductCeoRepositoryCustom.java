package com.woof.api.productCeo.repository.querydsl;

import com.woof.api.productCeo.model.ProductCeo;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
@Repository
public interface ProductCeoRepositoryCustom {
    public List<ProductCeo> findList();

    public Optional<ProductCeo> findList2(Long idx);
}
