package com.woof.api.member.service;

import com.woof.api.member.model.entity.EmailVerify;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.request.PostMemberSignupReq;
import com.woof.api.member.repository.EmailVerifyRepository;
import com.woof.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final JavaMailSender emailSender;
    private final EmailVerifyRepository emailVerifyRepository;
    private final MemberRepository memberRepository;
    public void sendEmail(PostMemberSignupReq request) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(request.getEmail());
//            helper.setSubject("[WOOF] " + request.getAuthority().substring(5) + " 회원가입 인증 메일입니다.");
            helper.setSubject("[WOOF] 회원가입 인증 메일입니다.");
            String uuid = UUID.randomUUID().toString();
            String url = "http://localhost:8080/user/confirm?email=" + request.getEmail() + "&uuid=" + uuid;

            // 이미지 파일 경로
            String imagePath = "https://github.com/hyungdoyou/devops/assets/148875644/f9dc322f-9d41-455d-b35c-e3cfcd7c008d";

            // HTML 문자열에 이미지 포함
            String content = "<html><body style= 'font-family: Pretendard; font-style: normal; font-weight: 500'>" +
                    "<p style='color: rgb(84, 29, 122); height: 100%; margin: 0; text-align: center; font-size: 30px; line-height: 3;'>" +
                    "<img src='" + imagePath + "' style='width: auto; height: auto;'/> <br/>" +
                    "<strong>WOOF</strong> 에 가입해주셔서 감사합니다" +
                    "</p>" + "<p style='color: #333; height: 100%; margin: 0; text-align: center; font-size: 25px; line-height: 3;'>" +
                    "이메일 인증 완료" + "</p>" + "<div style='text-align: center;'>\n" + "    <a href='" + url + "' style='color: #fff; text-decoration: none; background-color: rgb(84, 29, 122); padding: 10px 20px; border-radius: 5px; border: 2px solid rgb(84, 29, 122); display: inline-block; font-size: 20px; line-height: 2;'>\n" + "        이메일 인증하기\n" + "    </a>\n" + "</div>" + "</body></html>";
            helper.setText(content, true); // true는 HTML 메일임을 의미합니다.
            emailSender.send(message);
            // 이메일 인증 제한시간 추가하기-> redis
            create(request.getEmail(), uuid);
        } catch (MessagingException e) {
            e.printStackTrace();
            // 예외 처리 로직
        }
    }

    public void create(String email, String uuid) {
        EmailVerify emailVerify = EmailVerify.builder()
                .email(email)
                .uuid(uuid)
                .build();

        emailVerifyRepository.save(emailVerify);
    }

    public Boolean confirm(String email, String uuid){
        Optional<EmailVerify> result = emailVerifyRepository.findByEmail(email);

        if(result.isPresent()) {
            EmailVerify emailVerify = result.get();

            if(emailVerify.getUuid().equals(uuid)) {
                Optional<Member> member = memberRepository.findByMemberEmail(email);
                if (member.isPresent()) {
                    member.get().setStatus(true);
                    memberRepository.save(member.get());
                    return true;
                }
            }
        }
        return false;

    }

    public void updateStatus(String email) {
        Optional<Member> result = memberRepository.findByMemberEmail(email);
        if (result.isPresent()) {
            Member member = result.get();
            member.setStatus(true);
            memberRepository.save(member);
        }
    }
}
