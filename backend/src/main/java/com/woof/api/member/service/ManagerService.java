package com.woof.api.member.service;

import com.woof.api.member.model.entity.Manager;
import com.woof.api.member.model.requestdto.GetEmailConfirmReq;
import com.woof.api.member.model.requestdto.PostManagerSignupReq;
import com.woof.api.member.model.responsedto.PostManagerSignupRes;
import com.woof.api.member.repository.ManagerRepository;
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
public class ManagerService implements UserDetailsService {
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    public Manager getMemberByEmail(String email) {
        return managerRepository.findByEmail(email).get();
    }

    // Member CRUD
    public PostManagerSignupRes signup(PostManagerSignupReq postManagerSignupReq) {
        Manager manager = Manager.builder()
                .email(postManagerSignupReq.getEmail())
                .password(passwordEncoder.encode(postManagerSignupReq.getPassword()))
                .nickname(postManagerSignupReq.getNickname())
                .authority("ROLE_MANAGER")
                .status(false)
                .build();

        managerRepository.save(manager);

        Map<String, Long> result = new HashMap<>();
        result.put("idx", manager.getIdx());

        // 응답 형식
        PostManagerSignupRes postManagerSignupRes = PostManagerSignupRes.builder()
                .isSuccess(true)
                .code(1000L)
                .message("요청 성공.")
                .result(result)
                .success(true)
                .build();

        return postManagerSignupRes;
    }

    public Boolean getCheckEmail(GetEmailConfirmReq getEmailConfirmReq) {
        Optional<Manager> result = managerRepository.findByEmail(getEmailConfirmReq.getEmail());
        if (!result.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<Manager> result = managerRepository.findByEmail(username);
        Manager manager = null;
        if(result.isPresent()) {
            manager = result.get();
        }

        return manager;
    }
}