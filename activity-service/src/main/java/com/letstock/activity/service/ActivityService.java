package com.letstock.activity.service;

import com.letstock.follow.service.FollowService;
import com.letstock.activity.domain.Activity;
import com.letstock.activity.domain.ActivityType;
import com.letstock.activity.repository.ActivityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final FollowService followService;
    private final ActivityRepository activityRepository;
//    private final FeedRepository feedRepository;

    @Transactional
    public void act(Long memberId, Long targetBaseOwnerId, Long targetId, ActivityType type) {
        Long id = activityRepository.save(createActivity(memberId, targetBaseOwnerId, targetId, type)).getId();

        List<Long> needFeedIds = followService.getFollowersOf(memberId);
        needFeedIds.add(targetBaseOwnerId);

//        Set<Feed> feeds = needFeedIds.stream()
//                .map(needFeedId -> createFeed(needFeedId, id))
//                .collect(Collectors.toSet());

        // batch? bulk?
//        feedRepository.saveAll(feeds);
    }

    private Activity createActivity(Long memberId, Long targetBaseOwnerId, Long targetId, ActivityType type) {
        return Activity.builder()
                .memberId(memberId)
                .targetBaseOwnerId(targetBaseOwnerId)
                .targetId(targetId)
                .type(type)
                .build();
    }

//    private Feed createFeed(Long memberId, Long activityId) {
//        return Feed.builder()
//                .memberId(memberId)
//                .activityId(activityId)
//                .build();
//    }

}