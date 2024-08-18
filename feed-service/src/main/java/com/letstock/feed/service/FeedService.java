package com.letstock.feed.service;

import com.letstock.feed.dto.response.FeedResponse;
import com.letstock.feed.repository.FeedRepository;
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
    public List<FeedResponse> getListOf(Long memberId) {
        List<Object[]> results = feedRepository.findActivitiesWithMemberNamesByMemberId(memberId);

//        return results.stream()
//                .map(result -> {
//                    Activity activity = (Activity) result[0];
//                    String actMemberName = (String) result[1];
//                    String targetBaseOwnerName = (String) result[2];
//                    return new FeedResponse(activity, actMemberName, targetBaseOwnerName);
//                })
//                .collect(Collectors.toList());
        return results.stream()
                .map(result -> {
                    String actMemberName = String.valueOf(result[0]);
                    String targetBaseOwnerName = String.valueOf(result[1]);
                    return new FeedResponse(actMemberName, targetBaseOwnerName);
                })
                .collect(Collectors.toList());
    }

}
