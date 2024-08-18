package com.letstock.feed.repository;

import com.letstock.feed.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

//    @Query("SELECT a, m1.name as actMemberName, m2.name as targetBaseOwnerName " +
//            "FROM Feed f " +
//            "JOIN Activity a ON f.activityId = a.id " +
//            "LEFT JOIN Member m1 ON a.memberId = m1.id " +
//            "LEFT JOIN Member m2 ON a.targetBaseOwnerId = m2.id " +
//            "WHERE f.memberId = :memberId " +
//            "ORDER BY f.createdAt DESC")
//    List<Object[]> findActivitiesWithMemberNamesByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT f.memberId as actMemberName, f.memberId as targetBaseOwnerName " +
            "FROM Feed f " +
            "WHERE f.memberId = :memberId")
    List<Object[]> findActivitiesWithMemberNamesByMemberId(@Param("memberId") Long memberId);


}
