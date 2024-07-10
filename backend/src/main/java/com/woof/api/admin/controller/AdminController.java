package com.woof.api.admin.controller;

import com.woof.api.admin.model.request.PostSignUpAdminReq;
import com.woof.api.admin.service.AdminService;
import com.woof.api.common.BaseResponse;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.request.PatchMemberUpdateReq;
import com.woof.api.member.model.request.PostMemberLoginReq;
import com.woof.api.member.service.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Tag(name = "관리자", description = "Admin CRUD")
@Api(tags = "관리자 회원기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    private final MemberService memberService;
    private final AdminService adminService;

    @Operation(summary = "관리자 회원 가입", description = "관리자가 정보를 입력하여 회원 가입을 진행한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<BaseResponse> signup(
            @RequestBody @Valid PostSignUpAdminReq postSignUpAdminReq
    ) {
        BaseResponse baseRes = adminService.adminSignup(postSignUpAdminReq);
        return ResponseEntity.ok().body(baseRes);
    }

    @Operation(summary = "관리자 로그인", description = "관리자가 로그인을 시도한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<BaseResponse> login(@RequestBody @Valid PostMemberLoginReq postMemberLoginReq) {

        BaseResponse baseRes = memberService.login(postMemberLoginReq);
        return ResponseEntity.ok().body(baseRes);
    }

//    @Operation(summary = "관리자 정보 수정",
//            description = "관리자가 본인의 회원 정보를 수정한다.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "성공"),
//            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
//    })
//
//
//    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
//    public ResponseEntity<BaseResponse> update(@RequestPart(value = "admin") @Valid PatchMemberUpdateReq patchMemberUpdateReq
//    ) {
//        Member member = ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        BaseResponse baseRes = memberService.update(member.getEmail(), patchMemberUpdateReq);
//
//        return ResponseEntity.ok().body(baseRes);
//    }
//
//    @Operation(summary = "관리자 삭제", description = "관리자 정보를 삭제한다.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "성공"),
//            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
//    })
//    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{userIdx}")
//    public ResponseEntity<BaseResponse> delete(@PathVariable @NotNull @Positive Integer idx) {
//        BaseResponse baseRes = memberService.delete(idx);
//
//        return ResponseEntity.ok().body(baseRes);
//    }

}
