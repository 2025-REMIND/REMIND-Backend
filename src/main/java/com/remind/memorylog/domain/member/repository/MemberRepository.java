package com.remind.memorylog.domain.member.repository;

import com.remind.memorylog.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(String loginId); // 아이디 중복 검증
}
