package com.letstock.service.comment.controller;

import com.letstock.service.feed.domain.ActivityType;
import com.letstock.service.feed.service.ActivityService;
import com.letstock.service.member.config.security.MemberPrincipal;
import com.letstock.service.comment.dto.request.CommentCreate;
import com.letstock.service.comment.service.CommentService;
import com.letstock.service.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final ActivityService activityService;
    private final PostService postService;

    @PostMapping("/comments/{postId}")
    public void comment(@RequestBody @Valid CommentCreate commentCreate, @PathVariable Long postId,
                        @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Long memberId = memberPrincipal.getId();
        Long postOwnerId = postService.getOwnerId(postId);
        Long id = commentService.write(postId, commentCreate, memberId);
        activityService.act(memberId, postOwnerId, id, ActivityType.COMMENT);
    }

}