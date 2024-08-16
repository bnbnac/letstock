package com.letstock.service.like.controller;

import com.letstock.service.comment.service.CommentService;
import com.letstock.service.feed.service.ActivityService;
import com.letstock.service.like.domain.LikeType;
import com.letstock.service.like.dto.LikeForActDto;
import com.letstock.service.like.service.LikeService;
import com.letstock.service.member.config.security.MemberPrincipal;
import com.letstock.service.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;
    private final ActivityService activityService;
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/likes/{type}/{targetId}")
    public void like(@PathVariable String type, @PathVariable Long targetId,
                     @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Long memberId = memberPrincipal.getId();
        LikeType likeType = LikeType.getLikeType(type);

        Long targetBaseOwnerId = getOwnerId(likeType, targetId);
        LikeForActDto likeForActDto = likeService.like(likeType, targetId, memberId);

        activityService.act(memberId, targetBaseOwnerId, likeForActDto.getId(), likeForActDto.getActivityType());
    }

    private Long getOwnerId(LikeType likeType, Long targetId) {
        if (likeType == LikeType.POST) {
            return postService.getOwnerId(targetId);
        }

        return commentService.getOwnerId(targetId);
    }

}