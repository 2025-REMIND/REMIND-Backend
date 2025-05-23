package com.remind.memorylog.domain.member.repository;

import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.exception.MemberNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(String loginId); // 아이디 중복 검증
    Optional<Member> findByLoginId(String loginId); // 해당 로그인 아이디를 가진 사용자 반환

    default Member getMemberByMemberId(Long memberId) {
        return findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

}
