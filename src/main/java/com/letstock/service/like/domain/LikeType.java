package com.letstock.service.like.domain;

import java.util.Arrays;

public enum LikeType {
    POST("posts"),
    COMMENT("comments");

    private final String label;

    LikeType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static LikeType getLikeType(String type) {
        return Arrays.stream(LikeType.values())
                .filter(likeType -> likeType.label().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("check correct type string" + type));
    }
}