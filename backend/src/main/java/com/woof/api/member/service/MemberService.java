package com.woof.api.member.service;

import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.requestdto.PatchMemberUpdateReq;
import com.woof.api.member.model.requestdto.PostMemberSignupReq;
import com.woof.api.member.model.responsedto.GetMemberReadRes;
import com.woof.api.member.model.responsedto.PatchMemberUpdateRes;
import com.woof.api.member.model.responsedto.PostMemberSignupRes;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.orders.model.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getMemberByEmail (String email){
        return memberRepository.findByEmail(email).get();
    }

    // Member CRUD

    // create
    public PostMemberSignupRes signup(PostMemberSignupReq postMemberSignupReq){

        Optional<Member> duplicatedMember = memberRepository.findByEmail(postMemberSignupReq.getEmail());
        // 멤버 정보를 빌드로 저장
        if(!duplicatedMember.isPresent()) {

            Member member = Member.builder()
                    .email(postMemberSignupReq.getEmail())
                    .password(passwordEncoder.encode(postMemberSignupReq.getPassword()))
                    .nickname(postMemberSignupReq.getNickname())
                    .authority("ROLE_USER")
                    .status(false)
                    .build();

            memberRepository.save(member);

            Map<String, Long> result = new HashMap<>();
            result.put("idx", member.getIdx());

            PostMemberSignupRes postMemberSignupRes = PostMemberSignupRes.builder()
                    .isSuccess(true)
                    .code(1000L)
                    .message("요청 성공.")
                    .result(result)
                    .success(true)
                    .build();

            return postMemberSignupRes;

        } else {

            PostMemberSignupRes postMemberSignupRes = PostMemberSignupRes.builder()
                    .isSuccess(false)
                    .code(4000L)
                    .message("요청 실패. 중복된 이메일입니다.")
                    .result(null)
                    .success(false)
                    .build();

            return postMemberSignupRes;

        }


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<Member> result = memberRepository.findByEmail(username);
        Member member = null;
        if(result.isPresent()) {
            member = result.get();
        }

        return member;
    }

    // read
    // 내 정보 조회
    public GetMemberReadRes readMember (String username) {
        Optional<Member> result = memberRepository.findByEmail(username);

        Member member = result.get();

        GetMemberReadRes response = GetMemberReadRes.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .authority(member.getAuthority())
                .build();

        if (result.isPresent()) {
            return response;
        } else {
            return null;
        }

    }

    // update
    public PatchMemberUpdateRes updateMember (PatchMemberUpdateReq request) {
        Optional<Member> result = memberRepository.findByEmail(request.getEmail());

        if (result.isPresent()) {

            Member member = result.get();

            member.setNickname(request.getNickname());
            member.setPassword(passwordEncoder.encode(request.getPassword()));

            Member updateMember = memberRepository.save(member);

            PatchMemberUpdateRes response = PatchMemberUpdateRes.builder()
                    .nickname(updateMember.getNickname())
                    .password(updateMember.getPassword())
                    .build();
            return response;
        } else {
            return null;
        }

    }

    // delete


}