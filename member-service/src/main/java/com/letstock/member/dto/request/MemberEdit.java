package com.letstock.member.dto.request;

import lombok.Getter;

@Getter
public class MemberEdit {

    private String password;
    private String name;
    private String profileImage;
    private String greetings;

}