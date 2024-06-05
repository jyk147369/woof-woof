package com.woof.api.bookmark.service;


import com.woof.api.bookmark.model.Bookmark;
import com.woof.api.bookmark.model.dto.*;
import com.woof.api.bookmark.repository.BookmarkRepository;
import com.woof.api.bookmark.repository.querydsl.BookmarkRepositoryCustomImpl;
import com.woof.api.common.BaseRes;
import com.woof.api.common.Response;
import com.woof.api.member.model.entity.Member;
import com.woof.api.product.model.entity.ProductSchool;
import com.woof.api.product.model.entity.ProductManager;
import com.woof.api.product.repository.ProductManagerRepository;
import com.woof.api.product.repository.ProductSchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ProductSchoolRepository productSchoolRepository;
    private final ProductManagerRepository productManagerRepository;
    private final BookmarkRepositoryCustomImpl bookmarkRepositoryCustomImpl;

    @Transactional(readOnly = false)
    public BaseRes createBookmark(BookmarkCreateReq req) {

//        ProductSchool school = ProductSchoolRepository.findByIdx(req.getProductSchoolIdx())
//                .orElseThrow(() -> new )

        Bookmark bookmark = bookmarkRepository.save(Bookmark.builder()
                .member(Member.builder()
                        .idx(req.getMemberIdx())
                        .build())
                .productSchool(ProductSchool.builder()
                        .idx(req.getProductSchoolIdx())
                        .build())
                .productManager(ProductManager.builder()
                        .idx(req.getProductManagerIdx())
                        .build())
                .build());

        return BaseRes.builder()
                .isSuccess(true)
                .message("등록이 성공됐습니다.")
                .result(BookmarkCreateRes.builder()
                        .bookmarkIdx(bookmark.getIdx())
                        .memberIdx(bookmark.getMember().getIdx())
                        .productSchoolIdx(bookmark.getProductSchool().getIdx())
                        .productManagerIdx(bookmark.getProductManager().getIdx())
                        .build())
                .build();

    }

//     즐겨찾기 목록 조회
    public List<BookmarkListRes> cartList(Long memberIdx) {
        List<Bookmark> result = bookmarkRepositoryCustomImpl.findList(memberIdx);
        List<BookmarkListRes> list = new ArrayList<>();

        for (Bookmark bookmark : result) {
            BookmarkListRes bookmarkListRes = BookmarkListRes.builder()
                    .bookmarkIdx(bookmark.getIdx())
                    .memberIdx(bookmark.getMember().getIdx())
                    .productSchoolIdx(bookmark.getProductSchool().getIdx())
                    .productSchoolName(bookmark.getProductSchool().getStoreName())
                    .productManagerIdx(bookmark.getProductManager().getIdx())
                    .productMangerName(bookmark.getProductManager().getManagerName())
                    .build();
            list.add(bookmarkListRes);
        }
        return list;
    }


    // @Transactional
    public void cartRemove(Long idx) {
        bookmarkRepository.deleteById(idx);
    }

}






