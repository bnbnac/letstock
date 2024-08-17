package com.mod2.service.member.domain;


import lombok.Getter;

@Getter
public class MemberEditor {

    private String password;
    private String name;
    private String profileImage;
    private String greetings;

    private MemberEditor(String password, String name, String profileImage, String greetings) {
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
        this.greetings = greetings;
    }

    public static MemberEditorBuilder builder() {
        return new MemberEditorBuilder();
    }

    public static class MemberEditorBuilder {
        private String password;
        private String name;
        private String profileImage;
        private String greetings;

        MemberEditorBuilder() {
        }

        public MemberEditorBuilder password(String password) {
            if (password != null) {
                this.password = password;
            }
            return this;
        }

        public MemberEditorBuilder name(String name) {
            if (name != null) {
                this.name = name;
            }
            return this;
        }

        public MemberEditorBuilder profileImage(String profileImage) {
            if (profileImage != null) {
                this.profileImage = profileImage;
            }
            return this;
        }

        public MemberEditorBuilder greetings(String greetings) {
            if (greetings != null) {
                this.greetings = greetings;
            }
            return this;
        }

        public MemberEditor build() {
            return new MemberEditor(password, name, profileImage, greetings);
        }
    }
}
