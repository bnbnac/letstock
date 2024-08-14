package com.letstock.service.follow.repository;

import com.letstock.service.follow.domain.Follow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends CrudRepository<Follow, Long> {
    @Query("SELECT f.fromMemberId FROM Follow f WHERE f.toMemberId = :toMemberId")
    List<Long> findAllFromMemberIdsByToMemberId(@Param("toMemberId") Long toMemberId);
}