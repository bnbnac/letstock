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
@Table(name = "feeds")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private Long memberId;

    private Long activityId;

    @Builder
    public Feed(Long memberId, Long activityId) {
        Assert.notNull(memberId, "memberId must not be null");
        Assert.notNull(activityId, "activityId must not be null");

        this.createdAt = LocalDateTime.now();
        this.memberId = memberId;
        this.activityId = activityId;
    }

}
