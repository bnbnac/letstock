package com.letstock.service.feed.dto.response;

import com.letstock.service.feed.domain.Activity;
import com.letstock.service.feed.domain.ActivityType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ActivityResponse {

    private final Long id;
    private final LocalDateTime createdAt;
    private final Long actMemberId;
    private final String actMemberName;
    private final Long targetOwnerId;
    private final String targetOwnerName;
    private final Long targetId;
    private final ActivityType type;

    public ActivityResponse(Activity activity, String actMemberName, String targetOwnerName) {
        this.id = activity.getId();
        this.createdAt = activity.getCreatedAt();
        this.actMemberId = activity.getMemberId();
        this.actMemberName = actMemberName;
        this.targetOwnerId = activity.getTargetOwnerId();
        this.targetOwnerName = targetOwnerName;
        this.targetId = activity.getTargetId();
        this.type = activity.getType();
    }

}