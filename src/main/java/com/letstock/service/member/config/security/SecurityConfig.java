package com.letstock.service.member.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.letstock.service.member.config.jwt.JwtUtil;
import com.letstock.service.member.config.property.AuthProperty;
import com.letstock.service.member.config.security.filter.JwtAuthenticationFilter;
import com.letstock.service.member.config.security.filter.JwtAuthorizationFilter;
import com.letstock.service.member.config.security.handler.Http401Handler;
import com.letstock.service.member.config.security.handler.Http403Handler;
import com.letstock.service.member.config.security.handler.LoginFailHandler;
import com.letstock.service.member.config.security.handler.LoginSuccessHandler;
import com.letstock.service.member.controller.AuthUtil;
import com.letstock.service.member.domain.Member;
import com.letstock.service.member.exception.MemberNotFound;
import com.letstock.service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final Http401Handler http401Handler;
    private final Http403Handler http403Handler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final AuthUtil authUtil;
    private final AuthProperty authProperty;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/code/mail").permitAll()
                        .requestMatchers("/code/mail/verification").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), JwtAuthorizationFilter.class)
                .exceptionHandling(e -> {
                    e.accessDeniedHandler(http403Handler);
                    e.authenticationEntryPoint(http401Handler);
                })
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(objectMapper, "/auth/login");
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(loginFailHandler);
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authenticationUserDetailsService(memberRepository));
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public UserDetailsService authenticationUserDetailsService(MemberRepository memberRepository) {
        return email -> {
            Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFound::new);
            return new MemberPrincipal(member);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(
                jwtUtil, authUtil, authProperty, authorizationUserDetailsService()
        );
    }

    @Bean
    public UserDetailsService authorizationUserDetailsService() {
        return id -> {
            Member member = memberRepository.findById(Long.parseLong(id)).orElseThrow(MemberNotFound::new);
            return new MemberPrincipal(member);
        };
    }
}
