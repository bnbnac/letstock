package com.mod3.service.like.repository;

import com.mod3.service.like.domain.Like;
import com.mod3.service.like.domain.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByTypeAndTargetIdAndMemberId(LikeType type, Long targetId, Long memberId);
}
