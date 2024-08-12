package com.letstock.service.member.config.security;

import com.letstock.service.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class MemberPrincipal extends User {
    private final Long memberId;

    public MemberPrincipal(Member member) {
        super(member.getEmail(), member.getPassword(), List.of());
        this.memberId = member.getId();
    }
}
