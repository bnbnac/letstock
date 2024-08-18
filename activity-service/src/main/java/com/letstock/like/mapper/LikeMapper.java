package com.letstock.like.mapper;

import com.letstock.like.domain.Like;
import com.letstock.like.dto.LikeForActDto;
import com.letstock.like.dto.LikeForActDto;
import com.letstock.like.dto.LikeForActDto;
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