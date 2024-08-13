package com.letstock.service.member.repository;

import com.letstock.service.member.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String refreshToken);

    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.createdAt < :expiryTime")
    void deleteByCreatedAtBefore(LocalDateTime expiryTime);
}
