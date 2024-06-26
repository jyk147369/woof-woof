package com.woof.api.bookmark.repository.querydsl;


import com.woof.api.bookmark.model.Bookmark;
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

        QBookmark bookmark = new QBookmark("bookmark");
        QMember member = new QMember("member");

        List<Bookmark> result = from(bookmark)
                .leftJoin(bookmark.member, member).fetchJoin()
                .distinct().fetch();

        return result;
    }
}

