package com.letstock.service.feed.service;

import com.letstock.service.feed.domain.Activity;
import com.letstock.service.feed.dto.response.ActivityResponse;
import com.letstock.service.feed.repository.FeedRepository;
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
                    String fromMemberName = (String) result[1];
                    String toMemberName = (String) result[2];
                    return new ActivityResponse(activity, fromMemberName, toMemberName);
                })
                .collect(Collectors.toList());
    }

}
