package com.mod1.service.like.mapper;

import com.mod1.service.like.domain.Like;
import com.mod1.service.like.dto.LikeForActDto;
import com.mod1.service.like.domain.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    public LikeForActDto forActDto(Like like) {
        return LikeForActDto.builder()
                .id(like.getId())
                .activityType(like.generateActivityType())
                .build();
    }

}