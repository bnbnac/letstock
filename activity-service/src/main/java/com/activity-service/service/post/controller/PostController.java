package com.mod2.service.post.controller;

import com.mod2.service.feed.domain.ActivityType;
import com.mod2.service.feed.service.ActivityService;
import com.mod2.service.member.config.security.MemberPrincipal;
import com.mod2.service.post.dto.request.PostCreate;
import com.mod2.service.post.service.PostService;
import com.mod2.service.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final ActivityService activityService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate postCreate,
                     @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Long memberId = memberPrincipal.getId();
        Long id = postService.write(postCreate, memberId);
        activityService.act(memberId, memberId, id, ActivityType.POST);
    }

}
