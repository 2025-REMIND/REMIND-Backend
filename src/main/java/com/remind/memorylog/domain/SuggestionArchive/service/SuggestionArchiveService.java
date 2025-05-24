package com.remind.memorylog.domain.SuggestionArchive.service;

import com.remind.memorylog.domain.SuggestionArchive.web.dto.SuggestionArchiveResponse;

public interface SuggestionArchiveService {
    SuggestionArchiveResponse archive(Long suggestionId);
}
