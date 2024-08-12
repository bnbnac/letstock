package com.letstock.service.member.service;

import com.letstock.service.common.InvalidRequest;
import com.letstock.service.member.domain.Member;
import com.letstock.service.member.repository.MemberRepository;
import com.letstock.service.member.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final CodeService codeService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(Signup signup) {
        validateSignedUpMail(signup.getEmail());
        codeService.validateCode(signup.getCode(), signup.getEmail());

        String encodedPassword = passwordEncoder.encode(signup.getPassword());
        memberRepository.save(createMember(signup, encodedPassword));
    }

    private void validateSignedUpMail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new InvalidRequest("email", "이미 가입된 이메일입니다");
                });
    }

    private Member createMember(Signup signup, String encodedPassword) {
        return Member.builder()
                .email(signup.getEmail())
                .password(encodedPassword)
                .name(signup.getName())
                .profileImage(signup.getProfileImage())
                .greetings(signup.getGreetings())
                .build();
    }

}
