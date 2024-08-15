package com.letstock.service.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreate {

    @NotBlank(message = "콘텐츠를 입력해주세요")
    private String content;

}
