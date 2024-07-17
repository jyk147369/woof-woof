package com.woof.api.member.service;

import com.woof.api.common.BaseResponse;
import com.woof.api.member.exception.MemberAccountException;
import com.woof.api.member.exception.MemberDuplicateException;
import com.woof.api.member.exception.MemberNotFoundException;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.entity.MemberProfileImage;
import com.woof.api.member.model.request.*;
import com.woof.api.member.model.response.*;
import com.woof.api.member.repository.MemberProfileImageRepository;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberProfileImageService memberProfileImageService;
    private final EmailVerifyService emailVerifyService;
    private final MemberProfileImageRepository memberProfileImageRepository;
    private final PwEmailService pwEmailService;

    @Transactional
    public BaseResponse<PostMemberSignupRes> signup(PostMemberSignupReq request, MultipartFile profileImage, String role) {
        memberRepository.findByMemberEmail(request.getEmail()).ifPresent(member -> {
            throw MemberDuplicateException.forMemberId(request.getEmail());
        });

        Member member = Member.builder()
                .memberEmail(request.getEmail())
                .memberPw(passwordEncoder.encode(request.getPw()))
                .memberName(request.getName())
                .memberNickname(request.getNickname())
                .phoneNumber(request.getPhoneNumber())
                .petName(request.getPetName())
                .authority(role)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .status(false)
                .build();

        memberRepository.save(member);

        if (profileImage != null) {
            memberProfileImageService.registerMemberProfileImage(member, profileImage);
        } else {

        }

        PostMemberSignupRes response = PostMemberSignupRes.builder()
                .idx(member.getIdx())
                .email(member.getMemberEmail())
                .name(member.getMemberName())
                .role(member.getAuthority())
                .build();

        emailVerifyService.sendEmail(request);


        return BaseResponse.successRes("MEMBER_001", true, "회원이 등록되었습니다.", response);
    }

    public BaseResponse<PostMemberLoginRes> login(PostMemberLoginReq request){
        Member member = memberRepository.findByMemberEmail(request.getEmail()).orElseThrow(() ->
        MemberNotFoundException.forMemberEmail(request.getEmail()));

        PostMemberLoginRes response = PostMemberLoginRes.builder()
                .accessToken(JwtUtils.generateAccessToken(member, secretKey, expiredTimeMs))
                .build();

        if (passwordEncoder.matches(request.getPw(), member.getMemberPw()) && member.getStatus().equals(true)) {
            return BaseResponse.successRes("MEMBER_002", true, "로그인에 성공하였습니다.", response);
        } else {
            throw MemberAccountException.forInvalidPassword();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberEmail(username).orElseThrow(() ->
                MemberNotFoundException.forMemberEmail(username));

        return member;
    }
    public BaseResponse<GetMemberReadRes> read(){
        Member member = ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        memberRepository.findByMemberEmail(member.getMemberEmail()).orElseThrow(() ->
                MemberNotFoundException.forMemberEmail(member.getMemberEmail()));
        MemberProfileImage image = memberProfileImageRepository.findByMemberIdx(member.getIdx());

        if(image == null){
           image = memberProfileImageRepository.findByIdx(1L);
        }

        GetMemberReadRes response = GetMemberReadRes.builder()
                .email(member.getMemberEmail())
                .name(member.getMemberName())
                .nickname(member.getMemberNickname())
                .authority(member.getAuthority())
                .phoneNumber(member.getPhoneNumber())
                .petName(member.getPetName())
                .profileImage(image.getMemberImageAddr())
                .build();

        return BaseResponse.successRes("MEMBER_002", true, "회원조회에 성공하였습니다.", response);
    }
    public BaseResponse<PatchMemberUpdateRes> update(PatchMemberUpdateReq request){
        Member member = ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        memberRepository.findByMemberEmail(member.getMemberEmail()).orElseThrow(() ->
                MemberNotFoundException.forMemberEmail(member.getMemberEmail()));

        member.setPetName(request.getPetName());
        member.setMemberNickname(request.getNickname());
        member.setPhoneNumber(request.getPhoneNumber());

        if(request.getPw()!=null){
            // 기존 비밀번호랑 같을떄
            if(passwordEncoder.matches(request.getPw(), member.getPassword())){
                throw MemberAccountException.forDifferentPassword();
                // 바꾸는 비밀번호와 확인 비밀번호가 다를때
            } else if(!request.getPw().matches(request.getCheckPw())){
                throw MemberAccountException.forDifferentEachPassword();
            } else{
                member.setMemberPw(passwordEncoder.encode(request.getPw()));
            }
        }

        member.setUpdateAt(LocalDateTime.now());

        Member updateMember = memberRepository.save(member);

        PatchMemberUpdateRes response = PatchMemberUpdateRes.builder()
                .nickname(updateMember.getMemberNickname())
                .phoneNumber(updateMember.getPhoneNumber())
                .petName(updateMember.getPetName())
                .updatedAt(updateMember.getUpdateAt())
                .build();

        return BaseResponse.successRes("MEMBER_002", true, "회원 정보 수정에 성공하였습니다.", response);
    }

    public BaseResponse<PatchMemberUpdateImgRes> updateImg(MultipartFile profileImage){
        Member member = ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        memberProfileImageService.updateMemberProfileImage(member, profileImage);
        MemberProfileImage newImage = memberProfileImageRepository.findByMemberIdx(member.getIdx());

        member.setUpdateAt(LocalDateTime.now());

        Member updateMember = memberRepository.save(member);

        PatchMemberUpdateImgRes response = PatchMemberUpdateImgRes.builder()
                .profileImg(newImage.getMemberImageAddr())
                .updatedAt(updateMember.getUpdateAt())
                .build();

        return BaseResponse.successRes("MEMBER_002", true, "회원 프로필 이미지 수정에 성공하였습니다.", response);
    }

    // 회원정보 수정시 비밀번호 확인
    public BaseResponse<PostMemberCheckPwRes> checkPassword(PostMemberCheckPwReq request){
        Member member = ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (passwordEncoder.matches(request.getPw(), member.getPassword())) {
            PostMemberCheckPwRes response = PostMemberCheckPwRes.builder()
                    .email(member.getMemberEmail())
                    .build();
            return BaseResponse.successRes("MEMBER_002", true, "비밀번호가 확인되었습니다.", response);
        } else {
            throw  MemberAccountException.forInvalidPassword();
        }
    }

    // 회원 탈퇴
    public BaseResponse<PatchMemberCancelRes> cancel(){
        Member member = ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        memberRepository.findById(member.getIdx()).orElseThrow(() ->
                MemberNotFoundException.forMemberIdx(member.getIdx()));

        member.setStatus(false);
        member.setUpdateAt(LocalDateTime.now());

        Member cancelMember = memberRepository.save(member);

        PatchMemberCancelRes response = PatchMemberCancelRes.builder()
                .status(cancelMember.getStatus())
                .updatedAt(cancelMember.getUpdateAt())
                .build();

        return BaseResponse.successRes("MEMBER_002", true, "회원탈퇴가 완료되었습니다.", response);
    }

    // 이메일 찾기
    public BaseResponse<PostMemberFindEmailRes> findEmail(PostMemberFindEmailReq request){
        Member member = memberRepository.findByMemberNameAndPhoneNumber(request.getName(), request.getPhoneNumber()).orElseThrow(() ->
                MemberNotFoundException.forMemberNameAndPhoneNumber(request.getName(), request.getPhoneNumber()));

        PostMemberFindEmailRes response = PostMemberFindEmailRes.builder()
                .email(member.getMemberEmail())
                .build();

        return BaseResponse.successRes("MEMBER_002", true, "이메일 찾기에 성공하였습니다.", response);
    }

    // 비밀번호 찾기
    public BaseResponse<PostMemberFindPwRes> findPw(PostMemberFindPwReq request){
        Member member = memberRepository.findByMemberEmail(request.getEmail()).orElseThrow(() ->
                MemberNotFoundException.forMemberEmail(request.getEmail()));

        pwEmailService.findPassword(member.getMemberEmail(), member.getMemberName());

        Member updateMember = memberRepository.findByMemberEmail(member.getMemberEmail()).get();

        PostMemberFindPwRes response = PostMemberFindPwRes.builder()
                .updatedAt(updateMember.getUpdateAt())
                .build();

        return BaseResponse.successRes("MEMBER_002", true, "임시 비밀번호 전송에 성공하였습니다.", response);
    }

    //이창훈용 야매 메소드
    public void lch(Long idx) {
        Member member = memberRepository.findById(idx)
                .orElseThrow(() -> MemberNotFoundException.forMemberIdx(idx));
        member.setStatus(true);
        memberRepository.save(member);
    }
}