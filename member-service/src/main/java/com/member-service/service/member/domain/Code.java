package com.mod1.service.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "codes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String email;

    private boolean authed = false;

    private LocalDateTime createdAt;

    private LocalDateTime expiryDate;

    @Builder
    public Code(String code, String email, Long durationMinutes) {
        Assert.hasText(code, "code must not be null");
        Assert.hasText(email, "email must not be null");
        Assert.notNull(durationMinutes, "durationMinutes must not be null");

        LocalDateTime cur = LocalDateTime.now();

        this.code = code;
        this.email = email;
        this.createdAt = cur;
        this.expiryDate = cur.plusMinutes(durationMinutes);
    }

    public void auth() {
        this.authed = true;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
