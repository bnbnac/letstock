package com.letstock.service.member.service;

import com.letstock.service.common.InvalidRequest;
import com.letstock.service.member.domain.Member;
import com.letstock.service.member.domain.MemberEditor;
import com.letstock.service.member.dto.request.MemberEdit;
import com.letstock.service.member.exception.MemberNotFound;
import com.letstock.service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void edit(Long id, MemberEdit memberEdit, Long memberId) {
        validateMemberOwner(id, memberId);
        Member member = findMember(memberId);

        String password = null;
        if (memberEdit.getPassword() != null) {
            password = passwordEncoder.encode(memberEdit.getPassword());
        }
        MemberEditor memberEditor = createEditor(password, memberEdit, member);
        member.edit(memberEditor);
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);
    }

    private void validateMemberOwner(Long ownerId, Long requesterId) {
        if (!ownerId.equals(requesterId)) {
            throw new InvalidRequest("memberId", "소유자가 아닙니다");
        }
    }

    private MemberEditor createEditor(String password, MemberEdit memberEdit, Member member) {
        return member.toEditorBuilder()
                .password(password)
                .name(memberEdit.getName())
                .profileImage(memberEdit.getProfileImage())
                .greetings(memberEdit.getGreetings())
                .build();
    }
}
