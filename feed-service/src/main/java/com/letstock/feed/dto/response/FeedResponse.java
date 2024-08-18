package com.letstock.feed.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedResponse {

//    private final Long id;
//    private final LocalDateTime createdAt;
//    private final Long actMemberId;
    private final String actMemberName;
//    private final Long targetBaseOwnerId;
    private final String targetBaseOwnerName;
//    private final Long targetId;
//    private final ActivityType type;

//    public FeedResponse(Activity activity, String actMemberName, String targetBaseOwnerName) {
//        this.id = activity.getId();
//        this.createdAt = activity.getCreatedAt();
//        this.actMemberId = activity.getMemberId();
//        this.actMemberName = actMemberName;
//        this.targetBaseOwnerId = activity.getTargetBaseOwnerId();
//        this.targetBaseOwnerName = targetBaseOwnerName;
//        this.targetId = activity.getTargetId();
//        this.type = activity.getType();
//    }

    public FeedResponse(String actMemberName, String targetBaseOwnerName) {
//        this.id = activity.getId();
//        this.createdAt = activity.getCreatedAt();
//        this.actMemberId = activity.getMemberId();
        this.actMemberName = actMemberName;
//        this.targetBaseOwnerId = activity.getTargetBaseOwnerId();
        this.targetBaseOwnerName = targetBaseOwnerName;
//        this.targetId = activity.getTargetId();
//        this.type = activity.getType();
    }

}