package com.remind.memorylog.domain.Suggestion.web.dto;

import java.time.LocalDate;
import java.util.List;

public record GetArchivePageRes(
        List<GetArchiveSuggestionRes> done,
        List<GetArchiveSuggestionRes> progress,
        int totalPage,
        int totalData
) {
}
