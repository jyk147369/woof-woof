package com.woof.api.cart.repository.querydsl;


import com.woof.api.cart.model.Cart;
import com.woof.api.cart.model.QCart;
import com.woof.api.member.model.entity.QMember;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productCeo.model.QProductCeo;
import com.woof.api.productCeo.model.QProductCeoImage;
import com.woof.api.productManager.model.QProductManager;
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
        QProductCeo productCeo = new QProductCeo("productCeo");
        QProductManager productManager = new QProductManager("productManager");
        QMember member = new QMember("member");

        List<Cart> result = from(cart)
                .leftJoin(cart.productCeo, productCeo).fetchJoin()
                .leftJoin(cart.member, member).fetchJoin()
                .leftJoin(cart.productManager, productManager).fetchJoin()
                .distinct().fetch();

        return result;
    }
}

