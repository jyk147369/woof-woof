package com.woof.api.config;

import com.woof.api.member.model.entity.Ceo;
import com.woof.api.member.model.entity.Manager;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.service.CeoService;
import com.woof.api.member.service.ManagerService;
import com.woof.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final ManagerService managerService;
    private final MemberService memberService;
    private final CeoService ceoService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();


        UserDetails userDetails;
        if (username.startsWith("manager_")) {
            userDetails = (Manager) managerService.loadUserByUsername(username.substring(8,username.length()));
        } else if(username.startsWith("ceo_")) {
            userDetails = (Ceo) ceoService.loadUserByUsername(username.substring(4,username.length()));
        } else {
            userDetails = (Member) memberService.loadUserByUsername(username);
        }

        if (passwordEncoder.matches(password, userDetails.getPassword()) && userDetails.isEnabled()) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}