package com.letstock.member.repository;

import com.letstock.member.domain.RefreshToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String refreshToken);

    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.createdAt < :expiryTime")
    void deleteByCreatedAtBefore(LocalDateTime expiryTime);
}
