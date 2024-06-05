package com.woof.api.bookmark.repository;


import com.woof.api.bookmark.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findAllByMemberIdx(Long memberIdx);
}
