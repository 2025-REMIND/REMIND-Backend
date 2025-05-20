package com.remind.memorylog.domain.member.repository;

import com.remind.memorylog.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(String loginId); // 아이디 중복 검증
    Optional<Member> findByLoginId(String loginId); // 회원가입된 아이디가 있는지 확인
}
