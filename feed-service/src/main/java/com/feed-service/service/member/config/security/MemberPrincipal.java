package com.mod3.service.member.config.security;

import com.mod3.service.member.domain.Member;
import com.mod3.service.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class MemberPrincipal extends User {

    private final Long id;

    public MemberPrincipal(Member member) {
        super(member.getEmail(), member.getPassword(), List.of());
        this.id = member.getId();
    }

    public String getEmail() {
        return getUsername();
    }

}
