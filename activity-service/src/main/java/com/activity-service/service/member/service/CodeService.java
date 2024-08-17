package com.mod2.service.member.service;

import com.mod2.service.common.InvalidRequest;
import com.mod2.service.member.config.property.AuthProperty;
import com.mod2.service.member.domain.Code;
import com.mod2.service.member.repository.CodeRepository;
import com.mod2.service.common.InvalidRequest;
import com.mod2.service.member.config.property.AuthProperty;
import com.mod2.service.member.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CodeService {

    private final AuthProperty authProperty;
    private final CodeRepository codeRepository;

    @Transactional
    public void save(String code, String email) {
        List<Code> codes = codeRepository.findByEmail(email);
        codeRepository.deleteAll(codes);

        Code newCode = createCode(code, email, authProperty.getAuthCodeDurationMinutes());
        codeRepository.save(newCode);
    }

    private Code createCode(String code, String email, Long durationMinutes) {
        return Code.builder()
                .email(email)
                .code(code)
                .durationMinutes(durationMinutes)
                .build();
    }

    @Transactional
    public void verify(String code, String email) {
        Code foundCode = findCode(code, email);
        if (!isValidEmailAuth(foundCode)) {
            codeRepository.delete(foundCode);
            return;
        }

        foundCode.auth();
    }

    private boolean isValidEmailAuth(Code code) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(code.getCreatedAt(), now);

        if (duration.getSeconds() > authProperty.getEmailAuthCodeDurationMinutes() * 60) {
            throw new InvalidRequest("code", "expired code");
        }
        return true;
    }

    private Code findCode(String code, String mail) {
        return codeRepository.findTopByCodeAndEmailOrderByCreatedAtDesc(code, mail)
                .orElseThrow(() -> new InvalidRequest("code", "code does not match"));
    }

    public void validateCode(String code, String email) {
        Code foundCode = findCode(code, email);
        validateCodeExpiration(foundCode);
        validateAuthedCode(foundCode);
    }

    private void validateCodeExpiration(Code foundCode) {
        if (foundCode.isExpired()) {
            throw new InvalidRequest("email", "인증이 만료되었습니다.");
        }
    }

    private void validateAuthedCode(Code foundCode) {
        if (!foundCode.isAuthed()) {
            throw new InvalidRequest("email", "인증코드를 생성후 인증해주세요.");
        }
    }
}
