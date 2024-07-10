package com.woof.api.member.service;

import com.woof.api.common.BaseResponse;
import com.woof.api.member.exception.MemberAccountException;
import com.woof.api.member.exception.MemberDuplicateException;
import com.woof.api.member.exception.MemberNotFoundException;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.entity.MemberProfileImage;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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
        Member result = memberRepository.findByMemberEmail(member.getMemberEmail()).orElseThrow(() ->
                MemberNotFoundException.forMemberEmail(member.getMemberEmail()));
        MemberProfileImage image = memberProfileImageRepository.findByMemberIdx(member.getIdx());

        GetMemberReadRes response = GetMemberReadRes.builder()
                .email(result.getMemberEmail())
                .name(result.getMemberName())
                .nickname(result.getMemberNickname())
                .authority(result.getAuthority())
                .phoneNumber(result.getPhoneNumber())
                .petName(result.getPetName())
                .profileImage(image.getMemberImageAddr())
                .build();

        return BaseResponse.successRes("MEMBER_002", true, "회원조회에 성공하였습니다.", response);
    }
    public BaseResponse<PatchMemberUpdateRes> update(PatchMemberUpdateReq request){
        Member member = ((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Member result = memberRepository.findByMemberEmail(member.getMemberEmail()).orElseThrow(() ->
                MemberNotFoundException.forMemberEmail(member.getMemberEmail()));

        result.setPetName(request.getPetName());
        result.setMemberNickname(request.getNickname());
        result.setPhoneNumber(request.getPhoneNumber());

        Member updateMember = memberRepository.save(result);

        PatchMemberUpdateRes response = PatchMemberUpdateRes.builder()
                .nickname(updateMember.getMemberNickname())
                .phoneNumber(updateMember.getPhoneNumber())
                .petName(updateMember.getPetName())
                .build();

        return BaseResponse.successRes("MEMBER_002", true, "회원 정보 수정에 성공하였습니다.", response);
    }


    //이창훈용 야매 메소드
    public void lch(Long idx) {
        Member member = memberRepository.findById(idx)
                .orElseThrow(() -> MemberNotFoundException.forMemberIdx(idx));
        member.setStatus(true);
        memberRepository.save(member);
    }
}