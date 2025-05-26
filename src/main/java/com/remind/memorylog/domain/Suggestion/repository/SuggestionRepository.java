package com.remind.memorylog.domain.Suggestion.repository;

import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.domain.Suggestion.exception.SuggestionNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    Optional<Suggestion> findByMember_MemberIdAndCreatedAtBetween(Long memberId, LocalDateTime start, LocalDateTime end);

    default Suggestion getSuggestionBySuggestionId(Long suggestionId) {
        return findById(suggestionId).orElseThrow(SuggestionNotFoundException::new);
    }

}
