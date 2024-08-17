package com.letstock.service.like.mapper;

import com.letstock.service.like.domain.Like;
import com.letstock.service.like.dto.LikeForActDto;
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