package com.letstock.service.member.controller;

import com.letstock.service.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public Long get() {
        return memberService.get(1L).getHello();
    }

    @PostMapping("/members/{id}")
    public void hello(@PathVariable Long id) {
        memberService.hello(id);
    }

}
