package com.mod1.service.member.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod1.service.member.config.security.MemberPrincipal;
import com.mod1.service.member.domain.Member;
import com.mod1.service.member.exception.MemberNotFound;
import com.mod1.service.member.repository.MemberRepository;
import com.mod1.service.member.config.security.MemberPrincipal;
import com.mod1.service.member.domain.Member;
import com.mod1.service.member.exception.MemberNotFound;
import com.mod1.service.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(
            String loginUrl,
            ObjectMapper objectMapper,
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder
    ) {
        super(loginUrl);
        this.objectMapper = objectMapper;
        setCustomAuthenticationManager(memberRepository, passwordEncoder);
    }

    private void setCustomAuthenticationManager(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        UserDetailsService userDetailsService = email -> {
            Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFound::new);
            return new MemberPrincipal(member);
        };

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = new ProviderManager(provider);

        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        EmailPassword emailPassword = objectMapper.readValue(request.getInputStream(), EmailPassword.class);

        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                .unauthenticated(emailPassword.getEmail(), emailPassword.getPassword());

        token.setDetails(authenticationDetailsSource.buildDetails(request)); // remoteAddr, sessionId(미사용)
        return getAuthenticationManager().authenticate(token);
    }

    @Getter
    private static class EmailPassword {
        private String email;
        private String password;
    }
}
