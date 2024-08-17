package com.mod3.service.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String email;

    private String password;

    private String name;

    private String profileImage;

    private String greetings;

    @Builder
    public Member(String email, String name, String password, String profileImage, String greetings) {
        Assert.hasText(email, "email must not be null");
        Assert.hasText(name, "name must not be null");
        Assert.hasText(password, "password must not be null");
        Assert.hasText(profileImage, "profileImage must not be null");
        Assert.hasText(greetings, "greetings must not be null");

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
        this.greetings = greetings;
    }

    public MemberEditor.MemberEditorBuilder toEditorBuilder() {
        return MemberEditor.builder()
                .password(password)
                .name(name)
                .profileImage(profileImage)
                .greetings(greetings);
    }

    public void edit(MemberEditor memberEditor) {
        this.password = memberEditor.getPassword();
        this.name = memberEditor.getName();
        this.profileImage = memberEditor.getProfileImage();
        this.greetings = memberEditor.getGreetings();
        this.updatedAt = LocalDateTime.now();
    }
}
