package com.woof.api.admin.service;

import com.woof.api.admin.exception.AdminException;
import com.woof.api.admin.model.request.PostSignUpAdminReq;
import com.woof.api.admin.model.response.PostSignUpAdminRes;
import com.woof.api.common.BaseResponse;
import com.woof.api.common.error.ErrorCode;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    // 관리자 회원가입
    @Transactional(readOnly = false)
    public BaseResponse adminSignup(PostSignUpAdminReq postSignUpAdminReq) {
        Optional<Member> resultEmail = memberRepository.findByMemberEmail(postSignUpAdminReq.getEmail());

        // 중복된 이메일에 대한 예외처리
        if (resultEmail.isPresent()) {
            throw new AdminException(ErrorCode.DUPLICATE_SIGNUP_ID, String.format("SignUp Email [ %s ] is duplicated.", postSignUpAdminReq.getEmail()));
        }

        Member member = Member.builder().password(passwordEncoder.encode(postSignUpAdminReq.getPassword())).name(postSignUpAdminReq.getName()).email(postSignUpAdminReq.getEmail()).authority("ROLE_ADMIN").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).status(true).build();

        memberRepository.save(member);

        BaseResponse baseRes = BaseResponse.builder().isSuccess(true).message("관리자 가입에 성공하였습니다.").result(PostSignUpAdminRes.builder().email(user.getEmail()).name(user.getName()).build()).build();

        return baseRes;
    }
}
