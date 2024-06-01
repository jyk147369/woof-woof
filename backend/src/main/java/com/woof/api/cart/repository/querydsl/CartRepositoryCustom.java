package com.woof.api.cart.repository.querydsl;


import com.woof.api.cart.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepositoryCustom {
    public List<Cart> findList(Long memberIdx);
}
