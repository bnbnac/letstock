package com.letstock.member.controller;

import com.letstock.member.config.property.AuthProperty;
import com.letstock.member.config.property.JwtProperty;
import com.letstock.member.dto.request.Signup;
import com.letstock.member.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthController {

    private final AuthService authService;
    private final AuthUtil authUtil;
    private final AuthProperty authProperty;
    private final JwtProperty jwtProperty;

    @PostMapping("/auth/signup")
    public void signup(@RequestBody @Valid Signup signup) {
        authService.signup(signup);
    }

    @DeleteMapping("/auth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        try {
            Cookie[] cookies = authUtil.extractCookies(request);
            String refreshToken = authUtil.extractValueByCookieName(cookies, authProperty.getRefreshTokenCookieName());
            authService.logout(refreshToken);
        } catch (Exception e) {
            log.info("유효한 토큰이 없습니다. ip: {}", request.getRemoteAddr());
        }

        ResponseCookie accessTokenCookie = createExpiredCookie(authProperty.getAccessTokenCookieName());
        ResponseCookie refreshTokenCookie = createExpiredCookie(authProperty.getRefreshTokenCookieName());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();

    }

    private ResponseCookie createExpiredCookie(String cookieName) {
        return ResponseCookie.from(cookieName, "")
                .path("/")
                .httpOnly(true)
                .secure(jwtProperty.isCookieSecure())
                .maxAge(0)
                .sameSite("strict")
                .build();
    }

}
