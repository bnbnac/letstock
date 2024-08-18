package com.letstock.follow.controller;

import com.letstock.follow.service.FollowService;
import com.letstock.activity.domain.ActivityType;
import com.letstock.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;
    private final ActivityService activityService;

    @PostMapping("/follows/{toMemberId}")
    public void follow(@PathVariable Long toMemberId) {
//                       @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
//        Long fromMemberId = memberPrincipal.getId();
        Long fromMemberId = 1L;
        followService.follow(fromMemberId, toMemberId);
        activityService.act(fromMemberId, toMemberId, toMemberId, ActivityType.FOLLOW);
    }

}
