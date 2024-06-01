package com.woof.api.member.service;

import com.woof.api.member.model.entity.Manager;
import com.woof.api.member.model.entity.ManagerEmailVerify;
import com.woof.api.member.repository.ManagerRepository;
import com.woof.api.utils.TokenProvider;
import com.woof.api.member.repository.ManagerEmailVerifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagerEmailVerifyService {

    private final ManagerEmailVerifyRepository managerEmailVerifyRepository;
    private final JavaMailSender emailSender;
    private final ManagerRepository managerRepository;

    public Boolean confirm(String email, String uuid){
        Optional<ManagerEmailVerify> result = managerEmailVerifyRepository.findByEmail(email);

        if(result.isPresent()) {
            ManagerEmailVerify managerEmailVerify = result.get();

            if(managerEmailVerify.getUuid().equals(uuid)) {
                Optional<Manager> manager = managerRepository.findByEmail(email);
                if (manager.isPresent()) {
                    manager.get().setStatus(true);
                    managerRepository.save(manager.get());
                    return true;
                }
            }
        }
        return false;
    }

    public void create(String email, String uuid) {
        ManagerEmailVerify managerEmailVerify = ManagerEmailVerify.builder()
                .email(email)
                .uuid(uuid)
                .build();

        managerEmailVerifyRepository.save(managerEmailVerify);

    }

    public void sendManagerMail (String email, String role){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[MANAGER] woof 이메일 인증");
        // uuid 생성
        String uuid = UUID.randomUUID().toString();
        create(email,uuid);
        // jwt 생성
        String jwt = TokenProvider.generateAccessToken(email,role);
        //message.setText("http://3.35.233.95:8080/managerconfirm?email=" + email + "&uuid=" + uuid + "&jwt=" + jwt);
        message.setText("http://localhost:8080/managerconfirm?email=" + email + "&uuid=" + uuid + "&jwt=" + jwt);
        emailSender.send(message);
    }

    public void update(String email) {
        Optional<Manager> result = managerRepository.findByEmail(email);
        if (result.isPresent()) {
            Manager manager = result.get();
            manager.setStatus(true);
            managerRepository.save(manager);
        }
    }
}



