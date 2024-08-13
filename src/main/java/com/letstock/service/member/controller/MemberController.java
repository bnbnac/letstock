package com.letstock.service.member.controller;

import com.letstock.service.member.config.security.MemberPrincipal;
import com.letstock.service.member.dto.request.MemberEdit;
import com.letstock.service.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/members/{id}")
    public void edit(@RequestBody MemberEdit memberEdit, @PathVariable Long id,
                     @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        memberService.edit(id, memberEdit, memberPrincipal);
    }

}
