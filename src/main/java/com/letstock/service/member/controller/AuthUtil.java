package com.letstock.service.member.controller;

import com.letstock.service.member.config.property.AuthProperty;
import com.letstock.service.member.exception.Unauthorized;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
public class AuthUtil {

    private final AuthProperty authProperty;

    public Cookie[] extractCookies(HttpServletRequest servletRequest) {
        if (servletRequest == null) {
            log.error("servletRequest is null");
            throw new Unauthorized();
        }

        Cookie[] cookies = servletRequest.getCookies();
        if (cookies == null || cookies.length == 0) {
            log.error("cookie is null");
            throw new Unauthorized();
        }

        return cookies;
    }

    public String extractValueByCookieName(Cookie[] cookies, String cookieName) {
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        throw new Unauthorized();
    }

    public ResponseCookie createCookie(String cookieName, String cookieValue, Long maxAge) {
        return ResponseCookie.from(cookieName, cookieValue)
                .path("/")
                .httpOnly(true)
                .secure(authProperty.isCookieSecure())
                .maxAge(Duration.ofDays(maxAge))
                .sameSite(authProperty.getSameSite())
                .build();
    }

}
