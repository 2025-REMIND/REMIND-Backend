package com.remind.memorylog.domain.Suggestion.service;

import com.remind.memorylog.domain.Suggestion.web.dto.SuggestionArchiveResponse;

public interface SuggestionService {
    SuggestionArchiveResponse archiveSuggestion(Long suggestionId);
}
