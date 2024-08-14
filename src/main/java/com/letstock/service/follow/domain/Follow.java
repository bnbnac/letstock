package com.letstock.service.follow.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "follows", indexes = {
        @Index(name = "idx_to_member_id", columnList = "toMemberId")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private Long fromMemberId;

    private Long toMemberId;

    @Builder
    public Follow(Long fromMemberId, Long toMemberId) {
        Assert.notNull(fromMemberId, "fromMemberId must not be null");
        Assert.notNull(toMemberId, "toMemberId must not be null");

        this.createdAt = LocalDateTime.now();
        this.fromMemberId = fromMemberId;
        this.toMemberId = toMemberId;
    }

}
