package com.letstock.post.controller;

import com.letstock.activity.domain.ActivityType;
import com.letstock.activity.service.ActivityService;
import com.letstock.post.service.PostService;
import com.letstock.post.dto.request.PostCreate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final ActivityService activityService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate postCreate) {
//                     @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
//        Long memberId = memberPrincipal.getId();
        Long memberId = 1L;
        Long id = postService.write(postCreate, memberId);
        activityService.act(memberId, memberId, id, ActivityType.POST);
    }

}
