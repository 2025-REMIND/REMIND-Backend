package com.remind.memorylog.domain.SuggestionArchive.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionArchiveListResponse {
    private Long archiveId;
    private Long suggestionId;
}
