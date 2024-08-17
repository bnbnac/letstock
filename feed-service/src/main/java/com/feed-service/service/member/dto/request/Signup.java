package com.mod3.service.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Signup {

    @Email
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "프로필이미지를 입력해주세요")
    private String profileImage;

    @NotBlank(message = "인사말을 입력해주세요")
    private String greetings;

    @NotBlank(message = "인증코드를 입력해주세요")
    private String code;

}
