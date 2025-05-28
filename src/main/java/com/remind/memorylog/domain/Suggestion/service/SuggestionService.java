package com.remind.memorylog.domain.Suggestion.service;

import com.remind.memorylog.domain.Suggestion.web.dto.ArchiveSuggestionReq;
import com.remind.memorylog.domain.Suggestion.web.dto.GetArchivePageRes;
import com.remind.memorylog.domain.Suggestion.web.dto.GetTodaySuggestionRes;

public interface SuggestionService {
    GetTodaySuggestionRes getTodaySuggestion(Long memberId);

    void archiveSuggestion(ArchiveSuggestionReq archiveSuggestionReq, Long suggestionId);

    void unArchiveSuggestion(ArchiveSuggestionReq archiveSuggestionReq, Long suggestionId);

    GetArchivePageRes getArchiveSuggestion(Long memberId, int page, int size);
}
