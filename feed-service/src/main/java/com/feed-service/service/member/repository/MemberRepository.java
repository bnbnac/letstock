package com.mod3.service.member.repository;

import com.mod3.service.member.domain.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
