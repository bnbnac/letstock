package com.mod2.service.like.service;

import com.mod2.service.common.InvalidRequest;
import com.mod2.service.like.domain.Like;
import com.mod2.service.like.domain.LikeType;
import com.mod2.service.like.dto.LikeForActDto;
import com.mod2.service.like.mapper.LikeMapper;
import com.mod2.service.like.repository.LikeRepository;
import com.mod2.service.common.InvalidRequest;
import com.mod2.service.like.domain.Like;
import com.mod2.service.like.domain.LikeType;
import com.mod2.service.like.repository.LikeRepository;
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
