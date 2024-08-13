package com.letstock.service.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberEdit {

    private String password;
    private String name;
    private String profileImage;
    private String greetings;

    @Builder
    public MemberEdit(String password, String name, String profileImage, String greetings) {
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
        this.greetings = greetings;
    }
}
