package com.woof.api.member.service;

import com.woof.api.member.exception.MemberNotFoundException;
import com.woof.api.member.model.entity.EmailVerify;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.request.PostMemberSignupReq;
import com.woof.api.member.repository.EmailVerifyRepository;
import com.woof.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PwEmailService {
    private final JavaMailSender emailSender;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void findPassword(String email, String name) {

        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String str = updatePassword(email);

            helper.setTo(email);
            helper.setSubject("[WOOF] 임시 비밀번호 안내 이메일 입니다.");

            String imagePath = "https://github.com/hyungdoyou/devops/assets/148875644/f9dc322f-9d41-455d-b35c-e3cfcd7c008d";
            String content = "<html><body style= 'font-family: Pretendard; font-style: normal; font-weight: 500'> " +
                    "<p style='color: rgb(84, 29, 122); height: 100%; margin: 0; text-align: center; font-size: 30px; line-height: 3;'>" +
                    "<img src='" + imagePath + "' style='width: auto; height: auto;'/> <br/>" +
                    "[ " + name + " ] 회원님의 임시 비밀번호는" + "[ " + str + " ] 입니다. " +
                    "</p>" + "<p style='color: #333; height: 100%; margin: 0; text-align: center; font-size: 25px; line-height: 3;'>" +
                    "임시 비밀번호로 로그인 후 비밀번호를 변경해주세요 <br/>" + "</p>" + "<div style='text-align: center; line-height: 3;'>\n" +
                    "<a href=`http://www.localhost:8080` style='color: #fff; text-decoration: none; background-color: rgb(84, 29, 122); padding: 10px 20px; border-radius: 5px; border: 2px solid rgb(84, 29, 122); display: inline-block; font-size: 20px; line-height: 1;'>\n" +
                    "로그인 하기\n" + "</a>\n" + "</div>" + "</body></html>";

            helper.setText(content, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // 예외 처리 로직
        }
    }

    public String updatePassword(String email) {
        Member member = memberRepository.findByMemberEmail(email).orElseThrow(() ->
                MemberNotFoundException.forMemberEmail(email));

        String pw = getTempPassword();
        member.setMemberPw(passwordEncoder.encode(pw));
        member.setUpdateAt(LocalDateTime.now());

        memberRepository.save(member);

        return pw;
    }

    public String getTempPassword() {
        char[] charSet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        char[] charSet2 = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s',
                't','u','v','w','x','y','z'};

        char[] charSet3 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        String str = "";

        int idx1 = 0;
        int idx2 = 0;
        int idx3 = 0;

        for (int i = 0; i < 3; i++) {
            idx1 = (int) (charSet.length * Math.random());
            idx2 = (int) (charSet2.length * Math.random());
            idx3 = (int) (charSet3.length * Math.random());
            str += charSet[idx1];
            str += charSet2[idx2];
            str += charSet3[idx3];
        }
        str += "@";
        return str;
    }
}
