package com.mod3.service.feed.controller;

import com.mod3.service.feed.dto.response.ActivityResponse;
import com.mod3.service.feed.service.FeedService;
import com.mod3.service.member.config.security.MemberPrincipal;
import com.mod3.service.feed.dto.response.ActivityResponse;
import com.mod3.service.feed.service.FeedService;
import com.mod3.service.member.config.security.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/feeds")
    public List<ActivityResponse> getList(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        return feedService.getListOf(memberPrincipal.getId());
    }
}
