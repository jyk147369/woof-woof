package com.woof.api.member.service;

import com.woof.api.member.model.entity.MemberEmailVerify;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.repository.MemberEmailVerifyRepository;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberEmailVerifyService {

    private final MemberEmailVerifyRepository memberEmailVerifyRepository;
    private final JavaMailSender emailSender;
    private final MemberRepository memberRepository;
    public Boolean confirm(String email, String uuid){
        Optional<MemberEmailVerify> result = memberEmailVerifyRepository.findByEmail(email);

        if(result.isPresent()) {
            MemberEmailVerify memberEmailVerify = result.get();

            if(memberEmailVerify.getUuid().equals(uuid)) {
                Optional<Member> member = memberRepository.findByEmail(email);
                if (member.isPresent()) {
                    member.get().setStatus(true);
                    memberRepository.save(member.get());
                    return true;
                }
            }
        }
        return false;

    }

    public void create(String email, String uuid) {
        MemberEmailVerify memberEmailVerify = MemberEmailVerify.builder()
                .email(email)
                .uuid(uuid)
                .build();

        memberEmailVerifyRepository.save(memberEmailVerify);

    }

    public void sendMemberMail(String email, String role){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[Member] woof 이메일 인증");
        // uuid 생성
        String uuid = UUID.randomUUID().toString();
        create(email,uuid);
        // jwt 생성
        String jwt = TokenProvider.generateAccessToken(email, role);
        //message.setText("http://3.35.233.95:8080/memberconfirm?email=" + email + "&uuid=" + uuid + "&jwt=" + jwt);
        message.setText("http://localhost:8080/memberconfirm?email=" + email + "&uuid=" + uuid + "&jwt=" + jwt);
        emailSender.send(message);
    }

    public void update(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if (result.isPresent()) {
            Member member = result.get();
            member.setStatus(true);
            memberRepository.save(member);
        }
    }
}

