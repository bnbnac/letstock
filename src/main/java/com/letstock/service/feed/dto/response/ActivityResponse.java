package com.letstock.service.feed.dto.response;

import com.letstock.service.feed.domain.Activity;
import com.letstock.service.feed.domain.ActivityType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ActivityResponse {

    private final Long id;
    private final LocalDateTime createdAt;
    private final Long memberId;
    private final Long targetId;
    private final ActivityType type;
    private final String fromMemberName;
    private final String toMemberName;

    public ActivityResponse(Activity activity, String fromMemberName, String toMemberName) {
        this.id = activity.getId();
        this.createdAt = activity.getCreatedAt();
        this.memberId = activity.getMemberId();
        this.targetId = activity.getTargetId();
        this.type = activity.getType();
        this.fromMemberName = fromMemberName;
        this.toMemberName = (toMemberName == null) ? "" : toMemberName;
    }

}