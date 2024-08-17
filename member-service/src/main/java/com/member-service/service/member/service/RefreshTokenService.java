package com.mod1.service.member.service;

import com.mod1.service.common.InvalidRequest;
import com.mod1.service.member.config.property.AuthProperty;
import com.mod1.service.member.domain.RefreshToken;
import com.mod1.service.member.exception.TokenNotFound;
import com.mod1.service.member.repository.RefreshTokenRepository;
import com.mod1.service.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final AuthProperty authProperty;
    private final RefreshTokenRepository refreshTokenRepository;

    public String save(Long memberId) {
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = createRefreshToken(memberId, token);
        refreshTokenRepository.save(refreshToken);

        return token;
    }

    private RefreshToken createRefreshToken(Long memberId, String token) {
        return RefreshToken.builder()
                .memberId(memberId)
                .token(token)
                .durationDays(authProperty.getRefreshTokenCookieMaxAgeInDays())
                .build();
    }

    public void verify(String refreshTokenString, Long memberId) {
        RefreshToken foundToken = findRefreshToken(refreshTokenString);

        if (!foundToken.isOwnedBy(memberId)) {
            throw new InvalidRequest("memberId", "소유자가 아닙니다");
        }

        if (foundToken.isExpired()) {
            throw new InvalidRequest("token", "만료된 토큰입니다");
        }

        if (!foundToken.matches(refreshTokenString)) {
            throw new InvalidRequest("token", "토큰 불일치");
        }
    }

    public RefreshToken findRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(TokenNotFound::new);
    }

    public void delete(String token) {
        RefreshToken refreshToken = findRefreshToken(token);
        refreshTokenRepository.delete(refreshToken);
    }

}
