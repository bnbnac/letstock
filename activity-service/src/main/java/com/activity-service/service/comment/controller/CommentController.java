package com.mod2.service.comment.controller;

import com.mod2.service.comment.dto.request.CommentCreate;
import com.mod2.service.comment.service.CommentService;
import com.mod2.service.feed.domain.ActivityType;
import com.mod2.service.feed.service.ActivityService;
import com.mod2.service.member.config.security.MemberPrincipal;
import com.mod2.service.post.service.PostService;
import com.mod2.service.comment.service.CommentService;
import com.mod2.service.feed.domain.ActivityType;
import com.mod2.service.feed.service.ActivityService;
import com.mod2.service.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final ActivityService activityService;
    private final PostService postService;

    @PostMapping("/comments/{postId}")
    public void comment(@PathVariable Long postId, @RequestBody @Valid CommentCreate commentCreate,
                        @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Long memberId = memberPrincipal.getId();
        Long postOwnerId = postService.getOwnerId(postId);
        Long id = commentService.write(postId, commentCreate, memberId);
        activityService.act(memberId, postOwnerId, id, ActivityType.COMMENT);
    }

}