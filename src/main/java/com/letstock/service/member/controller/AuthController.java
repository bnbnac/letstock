package com.letstock.service.member.controller;

import com.letstock.service.member.config.security.MemberPrincipal;
import com.letstock.service.member.request.Signup;
import com.letstock.service.member.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public void signup(@RequestBody @Valid Signup signup) {
        authService.signup(signup);
    }

    @GetMapping("/auth/test")
    public String test(@AuthenticationPrincipal MemberPrincipal user) {
        return user.getUsername();
    }

}
