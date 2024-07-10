package com.woof.api.member.controller;

import com.woof.api.member.model.request.*;
import com.woof.api.member.service.EmailVerifyService;
import com.woof.api.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailVerifyService emailVerifyService;

    @ApiOperation(value="회원가입", notes="회원이 정보를 입력하여 회원가입한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/{role}/signup")
    public ResponseEntity<Object> signup(
            @PathVariable("role") String role,
            @RequestPart(value = "request") @Valid PostMemberSignupReq request,
            @RequestPart(value = "profileImage", required = false) MultipartFile memberProfileImage) {

        String userRole;
        if ("member".equals(role)) {
            userRole = "ROLE_MEMBER";
        } else if ("manager".equals(role)) {
            userRole = "ROLE_MANAGER";
        } else if ("ceo".equals(role)) {
            userRole = "ROLE_CEO";
        } else {
            return ResponseEntity.badRequest().body("Invalid role");
        }

        return ResponseEntity.ok().body(memberService.signup(request, memberProfileImage, userRole));
    }

    @ApiOperation(value="일반회원 이메일 인증확인", notes="일반회원이 회원가입시 이메일 인증한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/confirm")
    public RedirectView memberConfirm(GetMemberEmailConfirmReq getMemberEmailConfirmReq) {

        if (emailVerifyService.confirm(getMemberEmailConfirmReq.getEmail(), getMemberEmailConfirmReq.getUuid())) {
            emailVerifyService.updateStatus(getMemberEmailConfirmReq.getEmail());

            return new RedirectView("http://localhost:3000/" );
        } else {

            return new RedirectView("http://localhost:3000/emailCertError");
        }
    }

    @ApiOperation(value="일반회원 로그인", notes="일반회원이 정보를 입력하여 로그인한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<Object> login(@RequestBody @Valid PostMemberLoginReq request){
        return ResponseEntity.ok().body(memberService.login(request));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public ResponseEntity<Object> read(){
        return ResponseEntity.ok().body(memberService.read());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity<Object> update(@RequestBody @Valid PatchMemberUpdateReq request){
        return ResponseEntity.ok().body(memberService.update(request));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update/img")
    public ResponseEntity<Object> update(@RequestPart(value = "profileImage") MultipartFile memberProfileImage){
        return ResponseEntity.ok().body(memberService.updateImg(memberProfileImage));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkpw")
    public ResponseEntity<Object> checkPassword(@RequestBody @Valid PostMemberCheckPwReq request) {
        return ResponseEntity.ok().body(memberService.checkPassword(request));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/cancel")
    public ResponseEntity<Object> checkPassword() {
        return ResponseEntity.ok().body(memberService.cancel());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/find/email")
    public ResponseEntity<Object> findEmail(@RequestBody @Valid PostMemberFindEmailReq request) {
        return ResponseEntity.ok().body(memberService.findEmail(request));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/find/pw")
    public ResponseEntity<Object> findPw(@RequestBody @Valid PostMemberFindPwReq request) {
        return ResponseEntity.ok().body(memberService.findPw(request));
    }

    //이창훈 전용 야매 가입승인 코드
    @RequestMapping(method = RequestMethod.PATCH, value = "/lch/{idx}")
    public void lch(@PathVariable Long idx) {
        memberService.lch(idx);
    }
}
