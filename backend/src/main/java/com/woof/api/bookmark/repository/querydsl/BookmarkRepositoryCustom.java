package com.woof.api.bookmark.repository.querydsl;


import com.woof.api.bookmark.model.Bookmark;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepositoryCustom {
    public List<Bookmark> findList(Long memberIdx);
}
