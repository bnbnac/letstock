package com.letstock.service.follow.service;

import com.letstock.service.common.InvalidRequest;
import com.letstock.service.follow.domain.Follow;
import com.letstock.service.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public void follow(Long fromMemberId, Long toMemberId) {
        validateExistence(fromMemberId, toMemberId);
        followRepository.save(createFollow(fromMemberId, toMemberId));
    }

    private void validateExistence(Long fromMemberId, Long toMemberId) {
        if (followRepository.existsByFromMemberIdAndToMemberId(fromMemberId, toMemberId)) {
            throw new InvalidRequest("toMemberId", "already followed member");
        }
    }

    private Follow createFollow(Long fromMemberId, Long toMemberId) {
        return Follow.builder()
                .fromMemberId(fromMemberId)
                .toMemberId(toMemberId)
                .build();
    }

    public List<Long> getFollowersOf(Long memberId) {
        return followRepository.findAllFromMemberIdsByToMemberId(memberId);
    }
}