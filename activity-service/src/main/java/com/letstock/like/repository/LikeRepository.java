package com.letstock.like.repository;

import com.letstock.like.domain.Like;
import com.letstock.like.domain.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByTypeAndTargetIdAndMemberId(LikeType type, Long targetId, Long memberId);
}
