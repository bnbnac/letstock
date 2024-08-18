package com.letstock.member.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.letstock.member.config.jwt.JwtUtil;
import com.letstock.member.config.security.handler.LoginFailHandler;
import com.letstock.member.exception.MemberNotFound;
import com.letstock.member.config.property.AuthProperty;
import com.letstock.member.config.property.JwtProperty;
import com.letstock.member.config.security.filter.JwtAuthenticationFilter;
import com.letstock.member.config.security.filter.JwtAuthorizationFilter;
import com.letstock.member.config.security.filter.RefreshTokenFilter;
import com.letstock.member.config.security.handler.Http401Handler;
import com.letstock.member.config.security.handler.Http403Handler;
import com.letstock.member.config.security.handler.LoginSuccessHandler;
import com.letstock.member.controller.AuthUtil;
import com.letstock.member.domain.Member;
import com.letstock.member.repository.MemberRepository;
import com.letstock.member.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final Http401Handler http401Handler;
    private final Http403Handler http403Handler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final AuthUtil authUtil;
    private final JwtProperty jwtProperty;
    private final AuthProperty authProperty;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        List<String> permittedUrls = Arrays.asList("/auth/", "/code/");

        http
                .addFilterBefore(refreshTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthorizationFilter(permittedUrls), RefreshTokenFilter.class)
                .addFilterBefore(jwtAuthenticationFilter("/auth/login"), JwtAuthorizationFilter.class)

                .exceptionHandling(e -> {
                    e.accessDeniedHandler(http403Handler);
                    e.authenticationEntryPoint(http401Handler);
                })
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }

    private JwtAuthenticationFilter jwtAuthenticationFilter(String loginUrl) {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                loginUrl, objectMapper, memberRepository, passwordEncoder());
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        jwtAuthenticationFilter.setAuthenticationFailureHandler(loginFailHandler);
        return jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JwtAuthorizationFilter jwtAuthorizationFilter(List<String> permittedUrls) {
        return new JwtAuthorizationFilter(
                jwtUtil, authUtil, authProperty, authorizationUserDetailsService(), permittedUrls
        );
    }

    private UserDetailsService authorizationUserDetailsService() {
        return id -> {
            Member member = memberRepository.findById(Long.parseLong(id)).orElseThrow(MemberNotFound::new);
            return new MemberPrincipal(member);
        };
    }

    private RefreshTokenFilter refreshTokenFilter() {
        return new RefreshTokenFilter(
                authUtil, jwtUtil, authProperty, jwtProperty, refreshTokenService, authorizationUserDetailsService()
        );
    }

}
