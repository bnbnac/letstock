package com.letstock.service.member.scheduler;

import com.letstock.service.member.repository.CodeRepository;
import com.letstock.service.member.repository.RefreshTokenRepository;
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

    @Scheduled(fixedRate = 86_400_000)
    @Transactional
    public void deleteExpiredEntities() {
        LocalDateTime expiryTime = LocalDateTime.now().minusHours(1);
        codeRepository.deleteByCreatedAtBefore(expiryTime);
        refreshTokenRepository.deleteByCreatedAtBefore(expiryTime);
    }

}
