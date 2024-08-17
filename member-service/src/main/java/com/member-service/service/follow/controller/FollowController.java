package com.mod1.service.follow.controller;

import com.mod1.service.feed.domain.ActivityType;
import com.mod1.service.feed.service.ActivityService;
import com.mod1.service.follow.service.FollowService;
import com.mod1.service.member.config.security.MemberPrincipal;
import com.mod1.service.feed.domain.ActivityType;
import com.mod1.service.feed.service.ActivityService;
import com.mod1.service.follow.service.FollowService;
import com.mod1.service.member.config.security.MemberPrincipal;
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
        followService.follow(fromMemberId, toMemberId);
        activityService.act(fromMemberId, toMemberId, toMemberId, ActivityType.FOLLOW);
    }

}
