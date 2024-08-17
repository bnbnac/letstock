package com.letstock.service.member.config.security.filter;

import com.letstock.service.member.config.jwt.JwtUtil;
import com.letstock.service.member.config.property.AuthProperty;
import com.letstock.service.member.controller.AuthUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthUtil authUtil;
    private final AuthProperty authProperty;
    private final UserDetailsService userDetailsService;
    private final List<String> permittedUrls;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, AuthUtil authUtil, AuthProperty authProperty,
                                  UserDetailsService userDetailsService, List<String> permittedUrls) {
        this.jwtUtil = jwtUtil;
        this.authUtil = authUtil;
        this.authProperty = authProperty;
        this.userDetailsService = userDetailsService;
        this.permittedUrls = new ArrayList<>(permittedUrls);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        for (String permittedUrl : permittedUrls) {
            if (path.startsWith(permittedUrl)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        try {
            Cookie[] cookies = authUtil.extractCookies(request);
            String accessToken = authUtil.extractValueByCookieName(cookies, authProperty.getAccessTokenCookieName());

            Claims claims = jwtUtil.validateAndParseToken(accessToken).getPayload();
            setAuthentication(extractId(claims));
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            logger.info("Access token expired");
            request.setAttribute(AuthProperty.EXPIRED_TOKEN, e);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Authentication error", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

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