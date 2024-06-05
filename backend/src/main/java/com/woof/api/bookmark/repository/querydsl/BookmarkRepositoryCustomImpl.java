package com.woof.api.bookmark.repository.querydsl;


import com.woof.api.bookmark.model.Bookmark;
import com.woof.api.bookmark.model.QCart;
import com.woof.api.member.model.entity.QMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class BookmarkRepositoryCustomImpl extends QuerydslRepositorySupport implements BookmarkRepositoryCustom {
    public BookmarkRepositoryCustomImpl() {
        super(Bookmark.class);
    }

    @Override
    public List<Bookmark> findList(Long memberIdx) {

        QCart cart = new QCart("cart");
        QMember member = new QMember("member");

        List<Bookmark> result = from(cart)
                .leftJoin(cart.member, member).fetchJoin()
                .distinct().fetch();

        return result;
    }
}

