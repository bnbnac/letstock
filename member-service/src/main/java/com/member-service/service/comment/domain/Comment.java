package com.mod1.service.comment.domain;

import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long postId;

    private Long memberId;

    @Builder
    public Comment(Long postId, String content, Long memberId) {
        Assert.hasText(content, "content must not be null");
        Assert.notNull(postId, "postId cannot be null");
        Assert.notNull(memberId, "memberId must not be null");

        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.postId = postId;
        this.memberId = memberId;
    }

}
