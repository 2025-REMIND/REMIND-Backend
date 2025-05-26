package com.remind.memorylog.domain.Suggestion.service;

import com.remind.memorylog.domain.Suggestion.web.dto.ArchiveSuggestionReq;
import com.remind.memorylog.domain.Suggestion.web.dto.GetTodaySuggestionRes;
import org.springframework.transaction.annotation.Transactional;

public interface SuggestionService {
    GetTodaySuggestionRes getTodaySuggestion(Long memberId);

    @Transactional
    void archiveSuggestion(ArchiveSuggestionReq archiveSuggestionReq, Long suggestionId);

    @Transactional
    void unArchiveSuggestion(ArchiveSuggestionReq archiveSuggestionReq, Long suggestionId);
}
