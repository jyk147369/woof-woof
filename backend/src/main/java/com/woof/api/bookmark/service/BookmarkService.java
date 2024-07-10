package com.woof.api.bookmark.service;


import com.woof.api.bookmark.model.Bookmark;
import com.woof.api.bookmark.model.dto.*;
import com.woof.api.bookmark.repository.BookmarkRepository;
//import com.woof.api.bookmark.repository.querydsl.BookmarkRepositoryCustomImpl;
import com.woof.api.common.BaseResponse;
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
//    private final BookmarkRepositoryCustomImpl bookmarkRepositoryCustomImpl;

    @Transactional(readOnly = false)
    public BaseResponse createBookmark(BookmarkCreateReq bookmarkCreateReq) {

        Bookmark bookmark = bookmarkRepository.save(Bookmark.builder()
                .member(Member.builder()
                        .idx(bookmarkCreateReq.getMemberIdx())
                        .build())
                .productSchool(ProductSchool.builder()
                        .idx(bookmarkCreateReq.getProductSchoolIdx())
                        .build())
                .productManager(ProductManager.builder()
                        .idx(bookmarkCreateReq.getProductManagerIdx())
                        .build())
                .build());

        return BaseResponse.builder()
                .isSuccess(true)
                .message("즐겨찾기에 추가됐습니다.")
                .result(BookmarkCreateRes.builder()
                        .bookmarkIdx(bookmark.getIdx())
                        .memberIdx(bookmark.getMember().getIdx())
                        .productSchoolIdx(bookmark.getProductSchool().getIdx())
                        .productManagerIdx(bookmark.getProductManager().getIdx())
                        .build())
                .build();

    }

//     즐겨찾기 목록 조회
    public BaseResponse bookmarkList(Long memberIdx) {
        List<Bookmark> result = bookmarkRepository.findAllByMemberIdx(memberIdx);
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
        return BaseResponse.builder().isSuccess(true).message("조회 성공").result(list).build();
    }


    // @Transactional
    public BaseResponse deleteBookmark(Long bookmarkIdx) {
        bookmarkRepository.deleteById(bookmarkIdx);

        return BaseResponse.builder().isSuccess(true).message("삭제 성공").result("즐겨찾기 삭제되었습니다.").build();
    }

}






