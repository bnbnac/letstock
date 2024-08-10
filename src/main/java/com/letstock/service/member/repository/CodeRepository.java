package com.letstock.service.member.repository;

import com.letstock.service.member.domain.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findByEmail(String email);

    Optional<Code> findTopByCodeAndEmailOrderByCreatedAtDesc(String email, String code);

    @Modifying
    @Query("DELETE FROM Code c WHERE c.createdAt < :expiryTime")
    void deleteByCreatedAtBefore(LocalDateTime expiryTime);
}
