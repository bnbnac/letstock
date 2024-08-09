package com.letstock.service.member.service;

import com.letstock.service.member.domain.Member;
import com.letstock.service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member get(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    public void hello(Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        member.hello();
        memberRepository.save(member);
    }
}
