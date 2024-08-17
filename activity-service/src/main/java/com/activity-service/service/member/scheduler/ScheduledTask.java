package com.mod2.service.member.scheduler;

import com.mod2.service.member.repository.CodeRepository;
import com.mod2.service.member.repository.RefreshTokenRepository;
import com.mod2.service.member.repository.CodeRepository;
import com.mod2.service.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduledTask {

    private final CodeRepository codeRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Scheduled(fixedRate = 86_400_000)
    public void deleteExpiredEntities() {
        LocalDateTime expiryTime = LocalDateTime.now().minusHours(1);
        codeRepository.deleteByCreatedAtBefore(expiryTime);
        refreshTokenRepository.deleteByCreatedAtBefore(expiryTime);
    }

}
