package com.letstock.service.member.config.security.filter;

import com.letstock.service.member.config.jwt.JwtUtil;
import com.letstock.service.member.config.property.AuthProperty;
import com.letstock.service.member.controller.AuthUtil;
import com.letstock.service.member.exception.Unauthorized;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthUtil authUtil;
    private final AuthProperty authProperty;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        Cookie[] cookies = authUtil.extractCookies(request);
        String accessToken = authUtil.extractValueByCookieName(cookies, authProperty.getAccessTokenCookieName());

        try {
            Claims claims = jwtUtil.validateAndParseToken(accessToken).getPayload();
            setAuthentication(extractId(claims));
        } catch (ExpiredJwtException e) {
            throw new Unauthorized("Expired JWT token");
        }

        filterChain.doFilter(request, response);
    }

    private String extractId(Claims claims) {
        return claims.get(AuthProperty.ID, String.class);
    }

    private void setAuthentication(String id) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(createAuthentication(id));

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String id) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}