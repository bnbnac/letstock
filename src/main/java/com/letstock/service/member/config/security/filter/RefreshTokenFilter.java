package com.letstock.service.member.config.security.filter;

import com.letstock.service.member.config.jwt.JwtUtil;
import com.letstock.service.member.config.property.AuthProperty;
import com.letstock.service.member.config.property.JwtProperty;
import com.letstock.service.member.controller.AuthUtil;
import com.letstock.service.member.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@Slf4j
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;
    private final JwtUtil jwtUtil;
    private final AuthProperty authProperty;
    private final JwtProperty jwtProperty;
    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        Object expiredJwtExceptionObject = request.getAttribute(AuthProperty.EXPIRED_TOKEN);

        if (expiredJwtExceptionObject == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            ExpiredJwtException expiredJwtException = (ExpiredJwtException) expiredJwtExceptionObject;
            String memberId = extractId(expiredJwtException.getClaims());

            Cookie[] cookies = authUtil.extractCookies(request);
            String refreshTokenString = authUtil.extractValueByCookieName(
                    cookies, authProperty.getRefreshTokenCookieName());

            refreshTokenService.verify(refreshTokenString, Long.parseLong(memberId));

            JwtBuilder accessTokenBuilder = Jwts.builder().claim(AuthProperty.ID, memberId);
            String accessTokenJws = jwtUtil.generateJws(accessTokenBuilder, jwtProperty.getMaxAgeInMinutes());
            Cookie accessTokenCookie = createAccessTokenCookie(accessTokenJws);

            response.setStatus(SC_OK);
            response.addCookie(accessTokenCookie);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Error processing refresh token", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String extractId(Claims claims) {
        return claims.get(AuthProperty.ID, String.class);
    }

    private Cookie createAccessTokenCookie(String accessTokenJws) {
        return authUtil.createCookie(
                authProperty.getAccessTokenCookieName(),
                accessTokenJws,
                authProperty.getAccessTokenCookieMaxAgeInDays()
        );
    }

}