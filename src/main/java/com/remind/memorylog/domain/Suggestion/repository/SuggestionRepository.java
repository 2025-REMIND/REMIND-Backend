package com.remind.memorylog.domain.Suggestion.repository;

import com.remind.memorylog.domain.Suggestion.entity.ArchivedStatus;
import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.domain.Suggestion.exception.SuggestionNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    Optional<Suggestion> findByMember_MemberIdAndCreatedAtBetween(Long memberId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT s FROM Suggestion s " +
            "JOIN FETCH s.mission m " + // Mission과 조인
            "JOIN FETCH s.course c " + // Course와 조인
            "WHERE s.member.memberId = :memberId " +
            "AND s.archiveStatus = :status " +
            "AND c.status = 'DONE' " + // Course가 DONE 상태일 때만
            "AND m.status = 'DONE' " + // Mission이 DONE 상태일 때만
            "ORDER BY s.createdAt DESC")
    List<Suggestion> findArchivedSuggestionsDone(@Param("memberId") Long memberId,
                                                 @Param("status") ArchivedStatus status,
                                                 Pageable pageable);

    @Query("SELECT s FROM Suggestion s " +
            "JOIN FETCH s.mission m " + // Mission과 조인
            "JOIN FETCH s.course c " + // Course와 조인
            "WHERE s.member.memberId = :memberId " +
            "AND s.archiveStatus = :status " +
            "AND (c.status != 'DONE' OR m.status != 'DONE') " + // 하나라도 DONE이 아니면
            "ORDER BY s.createdAt DESC")
    List<Suggestion> findArchivedSuggestionsProgress(@Param("memberId") Long memberId,
                                                     @Param("status") ArchivedStatus status,
                                                     Pageable pageable);

    // 완료한 일정의 수 반환 (저장 여부 포함)
    @Query("""
    SELECT COUNT(s) FROM Suggestion s
    WHERE s.member.memberId = :memberId
    AND s.archiveStatus = :archiveStatus
    AND s.course.status = 'DONE'
    AND s.mission.status = 'DONE'
    """)
    int countArchivedSuggestionsDone(@Param("memberId") Long memberId,
                                     @Param("archiveStatus") ArchivedStatus status);

    // 진행 중인 일저정의 수 반환
    @Query("""
    SELECT COUNT(s) FROM Suggestion s
    WHERE s.member.memberId = :memberId
    AND s.archiveStatus = :archiveStatus
    AND s.course.status = 'PROGRESS'
    AND s.mission.status = 'PROGRESS'
    """)
    int countArchivedSuggestionsProgress(@Param("memberId") Long memberId,
                                     @Param("archiveStatus") ArchivedStatus status);

    default Suggestion getSuggestionBySuggestionId(Long suggestionId) {
        return findById(suggestionId).orElseThrow(SuggestionNotFoundException::new);
    }

}
