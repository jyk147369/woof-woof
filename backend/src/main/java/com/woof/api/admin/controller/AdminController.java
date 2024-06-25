package com.woof.api.admin.controller;

import com.woof.api.admin.model.request.PostSignUpAdminReq;
import com.woof.api.common.BaseRes;
import com.woof.api.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자", description = "Admin CRUD")
@Api(tags = "관리자 회원기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    private final MemberService memberService;

    @Operation(summary = "관리자 회원 가입", description = "관리자가 정보를 입력하여 회원 가입을 진행한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<BaseRes> signup(
            @RequestBody @Valid PostSignUpAdminReq postSignUpAdminReq
    ) {
        BaseRes baseRes = memberService.adminSignup(postSignUpAdminReq);
        return ResponseEntity.ok().body(baseRes);
    }

    @Operation(summary = "관리자 로그인", description = "관리자가 로그인을 시도한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<BaseRes> login(@RequestBody @Valid PostLoginUserReq postLoginUserReq) {

        BaseRes baseRes = memberService.login(postLoginUserReq);
        return ResponseEntity.ok().body(baseRes);
    }

    @Operation(summary = "관리자 정보 수정",
            description = "관리자가 본인의 회원 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity<BaseRes> update(@RequestPart(value = "admin") @Valid PatchUpdateUserReq patchUpdateUserReq
    ) {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        BaseRes baseRes = memberService.update(user.getEmail(), patchUpdateUserReq);

        return ResponseEntity.ok().body(baseRes);
    }

    @Operation(summary = "관리자 삭제", description = "관리자 정보를 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{userIdx}")
    public ResponseEntity<BaseRes> delete(@PathVariable @NotNull @Positive Integer userIdx) {
        BaseRes baseRes = memberService.delete(userIdx);

        return ResponseEntity.ok().body(baseRes);
    }

}
