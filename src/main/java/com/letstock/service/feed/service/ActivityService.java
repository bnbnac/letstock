package com.letstock.service.feed.service;

import com.letstock.service.feed.domain.Activity;
import com.letstock.service.feed.domain.ActivityType;
import com.letstock.service.feed.domain.Feed;
import com.letstock.service.feed.repository.ActivityRepository;
import com.letstock.service.feed.repository.FeedRepository;
import com.letstock.service.follow.service.FollowService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final FollowService followService;
    private final ActivityRepository activityRepository;
    private final FeedRepository feedRepository;

    @Transactional
    public void act(Long memberId, Long targetId, ActivityType type) {
        List<Long> followerIds = followService.getFollowersOf(memberId);
        List<Feed> feeds = new ArrayList<>();

        Long id = activityRepository.save(createActivity(memberId, targetId, type)).getId();

        for (Long followerId : followerIds) {
            feeds.add(createFeed(followerId, id));
        }

        // batch? bulk?
        feedRepository.saveAll(feeds);

    }

    private Activity createActivity(Long memberId, Long targetId, ActivityType type) {
        return Activity.builder()
                .memberId(memberId)
                .targetId(targetId)
                .type(type)
                .build();
    }

    private Feed createFeed(Long memberId, Long activityId) {
        return Feed.builder()
                .memberId(memberId)
                .activityId(activityId)
                .build();
    }

}