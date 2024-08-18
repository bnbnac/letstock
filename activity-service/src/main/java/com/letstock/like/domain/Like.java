package com.letstock.like.domain;


import com.letstock.activity.domain.ActivityType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private LikeType type;

    private Long targetId;

    private Long memberId;

    @Builder
    public Like(LikeType type, Long targetId, Long memberId) {
        Assert.notNull(type, "type must not be null");
        Assert.notNull(targetId, "targetId must not be null");
        Assert.notNull(memberId, "memberId must not be null");

        this.createdAt = LocalDateTime.now();
        this.type = type;
        this.targetId = targetId;
        this.memberId = memberId;
    }

    public ActivityType generateActivityType() {
        if (type == LikeType.POST) {
            return ActivityType.POST_LIKE;
        }

        return ActivityType.COMMENT_LIKE;
    }

}
