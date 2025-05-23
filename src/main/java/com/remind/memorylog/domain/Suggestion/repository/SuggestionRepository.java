package com.remind.memorylog.domain.Suggestion.repository;

import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
