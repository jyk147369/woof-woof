package com.woof.api.cart.repository.querydsl;


import com.woof.api.cart.model.Cart;
import com.woof.api.cart.model.QCart;
import com.woof.api.member.model.entity.QMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class CartRepositoryCustomImpl extends QuerydslRepositorySupport implements CartRepositoryCustom {
    public CartRepositoryCustomImpl() {
        super(Cart.class);
    }

    @Override
    public List<Cart> findList(Long memberIdx) {

        QCart cart = new QCart("cart");
        QMember member = new QMember("member");

        List<Cart> result = from(cart)
                .leftJoin(cart.member, member).fetchJoin()
                .distinct().fetch();

        return result;
    }
}

