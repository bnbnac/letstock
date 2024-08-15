package com.letstock.service.follow.controller;

import com.letstock.service.common.InvalidRequest;
import com.letstock.service.feed.domain.ActivityType;
import com.letstock.service.feed.service.ActivityService;
import com.letstock.service.follow.service.FollowService;
import com.letstock.service.member.config.security.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;
    private final ActivityService activityService;

    @PostMapping("/follows/{toMemberId}")
    public void follow(@PathVariable Long toMemberId,
                       @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Long fromMemberId = memberPrincipal.getId();
        if (fromMemberId.equals(toMemberId)) {
            throw new InvalidRequest("toMemberId", "cannot follow myself");
        }
        followService.follow(fromMemberId, toMemberId);
        activityService.act(fromMemberId, toMemberId, ActivityType.FOLLOW);
    }

}
