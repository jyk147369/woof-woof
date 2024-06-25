package com.woof.api.member.service;

import com.woof.api.common.BaseResponse;
import com.woof.api.member.exception.MemberAccountException;
import com.woof.api.member.exception.MemberDuplicateException;
import com.woof.api.member.exception.MemberNotFoundException;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.request.PatchMemberUpdateReq;
import com.woof.api.member.model.request.PostMemberLoginReq;
import com.woof.api.member.model.request.PostMemberSignupReq;
import com.woof.api.member.model.response.GetMemberReadRes;
import com.woof.api.member.model.response.PatchMemberUpdateRes;
import com.woof.api.member.model.response.PostMemberLoginRes;
import com.woof.api.member.model.response.PostMemberSignupRes;
import com.woof.api.member.repository.MemberProfileImageRepository;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberProfileImageService memberProfileImageService;
    private final MemberProfileImageRepository memberProfileImageRepository;

//    public Member getMemberByEmail (String email){
//        return memberRepository.findByMemberEmail(email).get();
//    }

    // Member CRUD

    // create
    @Transactional
    public BaseResponse<PostMemberSignupRes> signup(PostMemberSignupReq request, MultipartFile profileImage) {
        memberRepository.findByMemberEmail(request.getMemberEmail()).ifPresent(member -> {
            throw MemberDuplicateException.forMemberId(request.getMemberEmail());
        });

        Member member = Member.builder()
                .memberEmail(request.getMemberEmail())
                .memberPw(passwordEncoder.encode(request.getMemberPw()))
                .memberName(request.getMemberName())
                .memberNickname(request.getMemberNickname())
                .build();

        memberRepository.save(member);

        if (profileImage != null) {
            memberProfileImageService.registerMemberProfileImage(member, profileImage);
        }

        PostMemberSignupRes response =  PostMemberSignupRes.builder()
                .memberIdx(member.getIdx())
                .memberEmail(member.getMemberEmail())
                .memberName(member.getMemberName())
                .build();

        return BaseResponse.successRes("MEMBER_001", true, "회원이 등록되었습니다.", response);
    }

    public BaseResponse<PostMemberLoginRes> login(PostMemberLoginReq request){
        Member member = memberRepository.findByMemberEmail(request.getMemberEmail()).orElseThrow(() ->
        MemberNotFoundException.forMemberEmail(request.getMemberEmail()));

        PostMemberLoginRes response = PostMemberLoginRes.builder()
                .accessToken(JwtUtils.generateAccessToken(member, secretKey, expiredTimeMs))
                .build();

        if (passwordEncoder.matches(request.getMemberPw(), member.getMemberPw()) && member.getStatus().equals(true)) {
            return BaseResponse.successRes("MEMBER_002", true, "로그인에 성공하였습니다.", response);
        } else {
            throw MemberAccountException.forInvalidPassword();
        }
    }

    @Transactional
    public void sendEmail (PostMemberSignupReq request) {

    }

//    public PostMemberSignupRes signup(PostMemberSignupReq postMemberSignupReq){
//
//        Optional<Member> duplicatedMember = memberRepository.findByEmail(postMemberSignupReq.getEmail());
//        // 멤버 정보를 빌드로 저장
//        if(!duplicatedMember.isPresent()) {
//
//            Member member = Member.builder()
//                    .email(postMemberSignupReq.getEmail())
//                    .password(passwordEncoder.encode(postMemberSignupReq.getPassword()))
//                    .nickname(postMemberSignupReq.getNickname())
//                    .authority("ROLE_USER")
//                    .status(false)
//                    .build();
//
//            memberRepository.save(member);
//
//            Map<String, Long> result = new HashMap<>();
//            result.put("idx", member.getIdx());
//
//            PostMemberSignupRes postMemberSignupRes = PostMemberSignupRes.builder()
//                    .isSuccess(true)
//                    .code(1000L)
//                    .message("요청 성공.")
//                    .result(result)
//                    .success(true)
//                    .build();
//
//            return postMemberSignupRes;
//
//        } else {
//
//            PostMemberSignupRes postMemberSignupRes = PostMemberSignupRes.builder()
//                    .isSuccess(false)
//                    .code(4000L)
//                    .message("요청 실패. 중복된 이메일입니다.")
//                    .result(null)
//                    .success(false)
//                    .build();
//
//            return postMemberSignupRes;
//
//        }
//
//
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<Member> result = memberRepository.findByMemberEmail(username);
        Member member = null;
        if(result.isPresent()) {
            member = result.get();
        }

        return member;
    }

//    // read
//    // 내 정보 조회
//    public GetMemberReadRes readMember (String username) {
//        Optional<Member> result = memberRepository.findByEmail(username);
//
//        Member member = result.get();
//
//        GetMemberReadRes response = GetMemberReadRes.builder()
//                .email(member.getEmail())
//                .nickname(member.getNickname())
//                .authority(member.getAuthority())
//                .build();
//
//        if (result.isPresent()) {
//            return response;
//        } else {
//            return null;
//        }
//
//    }
//
//    // update
//    public PatchMemberUpdateRes updateMember (PatchMemberUpdateReq request) {
//        Optional<Member> result = memberRepository.findByEmail(request.getEmail());
//
//        if (result.isPresent()) {
//
//            Member member = result.get();
//
//            member.setNickname(request.getNickname());
//            member.setPassword(passwordEncoder.encode(request.getPassword()));
//
//            Member updateMember = memberRepository.save(member);
//
//            PatchMemberUpdateRes response = PatchMemberUpdateRes.builder()
//                    .nickname(updateMember.getNickname())
//                    .password(updateMember.getPassword())
//                    .build();
//            return response;
//        } else {
//            return null;
//        }
//
//    }

    // delete


}