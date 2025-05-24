package com.remind.memorylog.domain.SuggestionArchive.service;

import com.remind.memorylog.domain.SuggestionArchive.web.dto.SuggestionArchiveListResponse;
import com.remind.memorylog.domain.SuggestionArchive.web.dto.SuggestionArchiveResponse;

import java.util.List;

public interface SuggestionArchiveService {
    SuggestionArchiveResponse archive(Long suggestionId);
    List<SuggestionArchiveListResponse> getArchivedSuggestions(Long memberId);
}
