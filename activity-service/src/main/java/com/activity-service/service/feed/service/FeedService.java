package com.mod2.service.feed.service;

import com.mod2.service.feed.domain.Activity;
import com.mod2.service.feed.dto.response.ActivityResponse;
import com.mod2.service.feed.repository.FeedRepository;
import com.mod2.service.feed.domain.Activity;
import com.mod2.service.feed.dto.response.ActivityResponse;
import com.mod2.service.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;

    @Transactional
    public List<ActivityResponse> getListOf(Long memberId) {
        List<Object[]> results = feedRepository.findActivitiesWithMemberNamesByMemberId(memberId);

        return results.stream()
                .map(result -> {
                    Activity activity = (Activity) result[0];
                    String actMemberName = (String) result[1];
                    String targetBaseOwnerName = (String) result[2];
                    return new ActivityResponse(activity, actMemberName, targetBaseOwnerName);
                })
                .collect(Collectors.toList());
    }

}
