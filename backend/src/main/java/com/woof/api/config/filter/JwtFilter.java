package com.woof.api.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woof.api.common.error.ErrorCode;
import com.woof.api.common.error.ErrorResponse;
import com.woof.api.member.exception.MemberAccountException;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.utils.JwtUtils;
import com.woof.api.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public JwtFilter(String secretKey, MemberRepository memberRepository) {
        this.secretKey = secretKey;
        this.memberRepository = memberRepository;
    }

    private void handleJwtException(HttpServletResponse response, MemberAccountException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json");

        // ErrorResponse 생성 및 반환
        ErrorCode errorCode;
        if (exception.getErrorCode() != null) {
            errorCode = exception.getErrorCode();
        } else {
            errorCode = ErrorCode.UNAUTHORIZED;
        }

        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), exception.getMessage());
        String jsonErrorResponse = new ObjectMapper().writeValueAsString(errorResponse);

        response.getWriter().write(jsonErrorResponse);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            log.info("header = {}", header);
            String token;
            if (header != null && header.startsWith("Bearer ")) {
                token = header.split(" ")[1];
            } else {
                filterChain.doFilter(request, response);
                return;
            }

            String authority = JwtUtils.getAuthority(token, secretKey);

            if (authority.equals("ROLE_USER")) {
                String memberEmail = JwtUtils.getUserMemberEmail(token, secretKey);
                if (memberEmail != null) {
                    Optional<Member> result = memberRepository.findByMemberEmail(memberEmail);

                    if (result.isPresent()) {
                        Member member = result.get();

                        // 인가하는 코드
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                member, null,
                                member.getAuthorities()
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        filterChain.doFilter(request, response);
                    }
                }
            }
        } catch (MemberAccountException e) {
            // JwtUtils에서 던진 UserAccountException 처리
            handleJwtException(response, e);
        } catch (ServletException e) {
            // Spring Security 예외 처리
            handleJwtException(response, new MemberAccountException(ErrorCode.UNAUTHORIZED, e.getMessage()));
        }
    }
}