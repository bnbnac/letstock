package com.letstock.like.dto;

import com.letstock.activity.domain.ActivityType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class LikeForActDto {

    private final Long id;
    private final ActivityType activityType;

    @Builder
    public LikeForActDto(Long id, ActivityType activityType) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(activityType, "activityType must not be null");

        this.id = id;
        this.activityType = activityType;
    }

}