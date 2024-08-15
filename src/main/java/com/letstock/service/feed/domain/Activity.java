package com.letstock.service.feed.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "activities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private Long memberId;

    private Long targetId;

    private ActivityType type;

    @Builder
    public Activity(Long memberId, Long targetId, ActivityType type) {
        Assert.notNull(memberId, "memberId must not be null");
        Assert.notNull(targetId, "targetId must not be null");
        Assert.notNull(type, "type must not be null");

        this.createdAt = LocalDateTime.now();
        this.memberId = memberId;
        this.targetId = targetId;
        this.type = type;
    }

}