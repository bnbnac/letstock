package com.letstock.service.feed.repository;

import com.letstock.service.feed.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query("SELECT a, m1.name as fromMemberName, " +
            "CASE WHEN a.type = com.letstock.service.feed.domain.ActivityType.FOLLOW THEN m2.name ELSE NULL END as toMemberName " +
            "FROM Feed f " +
            "JOIN Activity a ON f.activityId = a.id " +
            "LEFT JOIN Member m1 ON a.memberId = m1.id " +
            "LEFT JOIN Member m2 ON a.type = com.letstock.service.feed.domain.ActivityType.FOLLOW AND a.targetId = m2.id " +
            "WHERE f.memberId = :memberId " +
            "ORDER BY f.createdAt DESC")
    List<Object[]> findActivitiesWithMemberNamesByMemberId(@Param("memberId") Long memberId);

}
