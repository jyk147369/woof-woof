package com.woof.api.bookmark.controller;

import com.woof.api.bookmark.model.dto.BookmarkCreateReq;
import com.woof.api.bookmark.service.BookmarkService;
import com.woof.api.common.BaseResponse;
import com.woof.api.member.model.entity.Member;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @ApiOperation(value="즐겨찾기 추가", notes="회원이 업체나 매니저를 즐겨찾기에 추가한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<BaseResponse> createBookmark(
            @AuthenticationPrincipal Member member,
            @RequestBody BookmarkCreateReq bookmarkCreateReq)
    {
        return ResponseEntity.ok().body(bookmarkService.createBookmark(bookmarkCreateReq));
    }

    @ApiOperation(value="즐겨찾기 조회", notes="회원이 자신의 즐겨찾기 목록을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<BaseResponse> bookmarkList(
            @AuthenticationPrincipal Member member)
    {
        return ResponseEntity.ok().body(bookmarkService.bookmarkList(member.getIdx()));
    }


    @ApiOperation(value="즐겨찾기 삭제", notes="회원이 즐겨찾기를 삭제한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/delete/{bookmarkIdx}")
    public ResponseEntity<BaseResponse> deleteBookmark(
            @AuthenticationPrincipal Member member,
            @PathVariable Long bookmarkIdx)
    {
        return ResponseEntity.ok().body(bookmarkService.deleteBookmark(bookmarkIdx));
    }

}
