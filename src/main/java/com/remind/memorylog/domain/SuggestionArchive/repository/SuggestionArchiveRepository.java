package com.remind.memorylog.domain.SuggestionArchive.repository;

import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.domain.SuggestionArchive.entity.SuggestionArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionArchiveRepository extends JpaRepository<SuggestionArchive, Long> {
    boolean existsBySuggestion(Suggestion suggestion); // 중복 저장 방지용

    List<SuggestionArchive> findBySuggestion_Member_MemberId(Long memberId); // 해당 회원의 보관된 제안 목록 조회
}
