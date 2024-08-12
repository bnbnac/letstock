package com.letstock.service.member.config.security.handler;

import com.letstock.service.member.config.jwt.JwtUtil;
import com.letstock.service.member.config.property.AuthProperty;
import com.letstock.service.member.config.property.JwtProperty;
import com.letstock.service.member.config.security.MemberPrincipal;
import com.letstock.service.member.controller.AuthUtil;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final AuthUtil authUtil;
    private final JwtProperty jwtProperty;
    private final AuthProperty authProperty;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        MemberPrincipal memberPrincipal = (MemberPrincipal) authentication.getPrincipal();
        log.info("onAuthenticationSuccess. id: {}", memberPrincipal.getId());

        JwtBuilder accessTokenBuilder = Jwts.builder()
                .claim(AuthProperty.ID, String.valueOf(memberPrincipal.getId()));

        String jws = jwtUtil.generateJws(accessTokenBuilder, jwtProperty.getMaxAgeInMinutes());
        Cookie accessTokenCookie = createAccessTokenCookie(jws);

        response.setStatus(SC_OK);
        response.addCookie(accessTokenCookie);
    }

    private Cookie createAccessTokenCookie(String accessTokenJws) {
        return authUtil.createCookie(
                authProperty.getAccessTokenCookieName(),
                accessTokenJws,
                authProperty.getAccessTokenCookieMaxAgeInDays()
        );
    }

}