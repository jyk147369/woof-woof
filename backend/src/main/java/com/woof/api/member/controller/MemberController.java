package com.woof.api.member.controller;

import com.woof.api.member.model.entity.Ceo;
import com.woof.api.member.model.entity.EmailVerify;
import com.woof.api.member.model.entity.Manager;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.request.*;
import com.woof.api.member.model.response.*;
import com.woof.api.member.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final ManagerService managerService;
    private final MemberService memberService;
    private final CeoService ceoService;
    private final ManagerEmailVerifyService managerEmailVerifyService;
    private final MemberEmailVerifyService memberEmailVerifyService;
    private final AuthenticationManager authenticationManager;
    private final EmailVerifyService emailVerifyService;

    @ApiOperation(value="일반회원 회원가입", notes="일반회원이 정보를 입력하여 회원가입한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<Object> signup (@RequestPart(value = "member") @Valid PostMemberSignupReq request,
                                          @RequestPart(value = "profileImage", required = false) MultipartFile profileImage){
        return ResponseEntity.ok().body(memberService.signup(request, profileImage, "ROLE_MEMBER"));
    }

    @ApiOperation(value="일반회원 이메일 인증확인", notes="일반회원이 회원가입시 이메일 인증한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/confirm")
    public RedirectView memberConfirm(GetEmailConfirmReq getEmailConfirmReq) {

        if (emailVerifyService.confirm(getEmailConfirmReq.getEmail(), getEmailConfirmReq.getUuid())) {
            emailVerifyService.updateStatus(getEmailConfirmReq.getEmail());

            return new RedirectView("http://localhost:3000/" );
        } else {

            return new RedirectView("http://localhost:3000/emailCertError");
        }
    }

    @ApiOperation(value="매니저회원 회원가입", notes="매니저회원이 정보를 입력하여 회원가입한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/manager/signup")
    public ResponseEntity signup (@RequestBody PostManagerSignupReq postManagerSignupReq){
        PostManagerSignupRes response = managerService.signup(postManagerSignupReq);
        managerEmailVerifyService.sendManagerMail(postManagerSignupReq.getEmail(), "ROLE_MANAGER");
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value="업체회원 회원가입", notes="업체회원이 정보를 입력하여 회원가입한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/ceo/signup")
    public ResponseEntity signup (@RequestBody PostCeoSignupReq postCeoSignupReq){
        PostCeoSignupRes response = ceoService.signup(postCeoSignupReq);
        return ResponseEntity.ok().body(response);
    }



    @ApiOperation(value="매니저회원 이메일 인증확인", notes="매니저회원이 회원가입시 이메일 인증한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/managerconfirm")
    public RedirectView managerConfirm(GetEmailConfirmReq getEmailConfirmReq) {

        if (managerEmailVerifyService.confirm(getEmailConfirmReq.getEmail(), getEmailConfirmReq.getUuid())) {
            managerEmailVerifyService.update(getEmailConfirmReq.getEmail());

            return new RedirectView("http://localhost:3000/emailconfirm/");
        } else {

            return new RedirectView("http://localhost:3000/emailCertError");
        }
    }

    @ApiOperation(value="일반회원 로그인", notes="일반회원이 정보를 입력하여 로그인한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/member/login")
    public ResponseEntity<Object> login(@RequestBody @Valid PostMemberLoginReq request){
        return ResponseEntity.ok().body(memberService.login(request));
    }

    @ApiOperation(value="매니저회원 로그인", notes="매니저회원이 정보를 입력하여 로그인한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/manager/login")
    public ResponseEntity login(@RequestBody PostManagerLoginReq request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("manager_"+request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        if(authentication.getPrincipal() != null) {
//            Manager manager = (Manager) authentication.getPrincipal();
//            return ResponseEntity.ok().body(PostManagerLoginRes.builder().accessToken(TokenProvider.generateAccessToken(manager.getUsername(), "ROLE_MANAGER")).build());
//        }
        return ResponseEntity.badRequest().body("에러");
    }

    @ApiOperation(value="업체회원 로그인", notes="업체회원이 정보를 입력하여 로그인한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/ceo/login")
    public ResponseEntity login(@RequestBody PostCeoLoginReq request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("ceo_"+request.getBusinessnum(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        if(authentication.getPrincipal() != null) {
//            Ceo ceo = (Ceo) authentication.getPrincipal();
//            return ResponseEntity.ok().body(PostManagerLoginRes.builder().accessToken(TokenProvider.generateAccessToken(ceo.getUsername(), "ROLE_CEO")).build());
//        }
        return ResponseEntity.badRequest().body("에러");
    }

//    @ApiOperation(value="매니저회원 테스트", notes="매니저회원 로그인 테스트")
//    @RequestMapping(method = RequestMethod.GET, value = "/test/manager")
//    public String testManager() {
//        return "성공";
//    }
//
//    @ApiOperation(value="일반회원 테스트", notes="일반회원 로그인 테스트")
//    @RequestMapping(method = RequestMethod.GET, value = "/test/member")
//    public String testMember() {
//        return "성공";
//    }

//    @ApiOperation(value="일반회원 마이페이지 조회", notes="일반회원이 이메일을 입력하고 정보를 조회한다.")
//    @GetMapping("/member/{email}")
//    public ResponseEntity read(@PathVariable String email) {
//        GetMemberReadRes response = memberService.readMember(email);
//        return ResponseEntity.ok().body(response);
//    }

//    @ApiOperation(value="일반회원 정보 수정", notes="일반회원이 정보를 수정한다.")
//    @RequestMapping(method = RequestMethod.PATCH, value = "/member/update")
//    public ResponseEntity update (@RequestBody PatchMemberUpdateReq request) {
//        PatchMemberUpdateRes response = memberService.updateMember(request);
//        return ResponseEntity.ok().body(response);
//    }


}
