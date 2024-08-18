package com.letstock.feed.controller;

import com.letstock.feed.dto.response.FeedResponse;
import com.letstock.feed.service.FeedService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/feeds")
//    public List<ActivityResponse> getList(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
    public List<FeedResponse> getList() {
//        return feedService.getListOf(memberPrincipal.getId());
        return feedService.getListOf(1L);
    }
}
