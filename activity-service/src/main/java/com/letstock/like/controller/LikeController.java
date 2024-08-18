package com.letstock.like.controller;

import com.letstock.activity.service.ActivityService;
import com.letstock.comment.service.CommentService;
import com.letstock.like.domain.LikeType;
import com.letstock.like.service.LikeService;
import com.letstock.post.service.PostService;
import com.letstock.like.dto.LikeForActDto;
import lombok.RequiredArgsConstructor;
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
    public void like(@PathVariable String type, @PathVariable Long targetId) {
//                     @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
//        Long memberId = memberPrincipal.getId();
        Long memberId = 1L;
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
