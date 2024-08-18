package com.letstock.member.config.security;

import com.letstock.member.domain.Member;
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
