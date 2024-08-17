package com.mod2.service.feed.dto.response;

import com.mod2.service.feed.domain.Activity;
import com.mod2.service.feed.domain.ActivityType;
import com.mod2.service.feed.domain.Activity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ActivityResponse {

    private final Long id;
    private final LocalDateTime createdAt;
    private final Long actMemberId;
    private final String actMemberName;
    private final Long targetBaseOwnerId;
    private final String targetBaseOwnerName;
    private final Long targetId;
    private final ActivityType type;

    public ActivityResponse(Activity activity, String actMemberName, String targetBaseOwnerName) {
        this.id = activity.getId();
        this.createdAt = activity.getCreatedAt();
        this.actMemberId = activity.getMemberId();
        this.actMemberName = actMemberName;
        this.targetBaseOwnerId = activity.getTargetBaseOwnerId();
        this.targetBaseOwnerName = targetBaseOwnerName;
        this.targetId = activity.getTargetId();
        this.type = activity.getType();
    }

}