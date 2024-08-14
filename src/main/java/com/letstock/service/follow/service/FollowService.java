package com.letstock.service.follow.service;

import com.letstock.service.follow.domain.Follow;
import com.letstock.service.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public void follow(Long toMemberId, Long fromMemberId) {
        Follow follow = createFollow(toMemberId, fromMemberId);
        followRepository.save(follow);
    }

    private Follow createFollow(Long toMemberId, Long fromMemberId) {
        return Follow.builder()
                .fromMemberId(fromMemberId)
                .toMemberId(toMemberId)
                .build();
    }

    public List<Long> getFollowersOf(Long memberId) {
        return followRepository.findAllFromMemberIdsByToMemberId(memberId);
    }
}
