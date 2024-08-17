package com.mod2.service.like.controller;

import com.mod2.service.comment.service.CommentService;
import com.mod2.service.feed.service.ActivityService;
import com.mod2.service.like.domain.LikeType;
import com.mod2.service.like.dto.LikeForActDto;
import com.mod2.service.like.service.LikeService;
import com.mod2.service.member.config.security.MemberPrincipal;
import com.mod2.service.post.service.PostService;
import com.mod2.service.post.service.PostService;
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
