package com.woof.api.config;

import com.woof.api.config.filter.JwtFilter;
import com.woof.api.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final TokenProvider tokenProvider;
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        try {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    //.antMatchers("/v2/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                    .antMatchers("/member/*").permitAll()
                    .antMatchers("/ceo/*").permitAll()
                    .antMatchers("/productCeo/*").permitAll()
                    .antMatchers("/productManager/*").permitAll()
                    .antMatchers("/test/ceo").hasRole("CEO")
                    .antMatchers("/test/member").hasRole("MEMBER")
                    .antMatchers("/orders/**").permitAll() // 인증된 사용자만 접근 허용
                    .antMatchers("/**").permitAll()
                    .anyRequest().permitAll();

            http.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

            http.formLogin().disable();

            return http.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

