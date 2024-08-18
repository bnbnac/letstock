package com.letstock.like.service;

import com.letstock.common.InvalidRequest;
import com.letstock.like.domain.Like;
import com.letstock.like.domain.LikeType;
import com.letstock.like.dto.LikeForActDto;
import com.letstock.like.mapper.LikeMapper;
import com.letstock.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    public LikeForActDto like(LikeType type, Long targetId, Long memberId) {
        validateLikeAlready(type, targetId, memberId);
        Like like = likeRepository.save(createLike(type, targetId, memberId));
        return likeMapper.forActDto(like);
    }

    private void validateLikeAlready(LikeType type, Long targetId, Long memberId) {
        likeRepository.findByTypeAndTargetIdAndMemberId(type, targetId, memberId).ifPresent(like -> {
            throw new InvalidRequest("targetId", "already liked");
        });
    }

    private Like createLike(LikeType type, Long targetId, Long memberId) {
        return Like.builder()
                .type(type)
                .targetId(targetId)
                .memberId(memberId)
                .build();
    }

}
