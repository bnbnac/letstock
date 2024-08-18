package com.letstock.comment.controller;

import com.letstock.activity.domain.ActivityType;
import com.letstock.activity.service.ActivityService;
import com.letstock.comment.dto.request.CommentCreate;
import com.letstock.comment.service.CommentService;
import com.letstock.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public void comment(@PathVariable Long postId, @RequestBody @Valid CommentCreate commentCreate) {
//                        @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
//    Long memberId = memberPrincipal.getId();
        Long memberId = 1L;
        Long postOwnerId = postService.getOwnerId(postId);
        Long id = commentService.write(postId, commentCreate, memberId);
        activityService.act(memberId, postOwnerId, id, ActivityType.COMMENT);
    }

}