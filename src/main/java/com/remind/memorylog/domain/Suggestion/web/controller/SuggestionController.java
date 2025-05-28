package com.remind.memorylog.domain.Suggestion.web.controller;

import com.remind.memorylog.domain.Suggestion.service.SuggestionService;
import com.remind.memorylog.domain.Suggestion.web.dto.ArchiveSuggestionReq;
import com.remind.memorylog.domain.Suggestion.web.dto.GetArchivePageRes;
import com.remind.memorylog.domain.Suggestion.web.dto.GetTodaySuggestionRes;
import com.remind.memorylog.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionController {
    private final SuggestionService suggestionService;

    @GetMapping("/today/{memberId}")
    public ResponseEntity<SuccessResponse<GetTodaySuggestionRes>> getTodaySuggestion(@PathVariable("memberId") Long memberId) {
        GetTodaySuggestionRes getTodaySuggestionRes = suggestionService.getTodaySuggestion(memberId);
        return ResponseEntity.ok(SuccessResponse.ok(getTodaySuggestionRes));
    }

    @PostMapping("/{suggestionId}/archive")
    public ResponseEntity<SuccessResponse<?>> archiveSuggestion(@PathVariable("suggestionId") Long suggestionId,
                                                                @RequestBody ArchiveSuggestionReq archiveSuggestionReq) {
        suggestionService.archiveSuggestion(archiveSuggestionReq, suggestionId);
        return ResponseEntity.ok(SuccessResponse.empty());
    }

    @DeleteMapping("/{suggestionId}/delete")
    public ResponseEntity<SuccessResponse<?>> unArchiveSuggestion(@PathVariable("suggestionId") Long suggestionId,
                                                                @RequestBody ArchiveSuggestionReq archiveSuggestionReq) {
        suggestionService.unArchiveSuggestion(archiveSuggestionReq, suggestionId);
        return ResponseEntity.ok(SuccessResponse.empty());
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<SuccessResponse<GetArchivePageRes>> getArchivePage(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        GetArchivePageRes response = suggestionService.getArchiveSuggestion(memberId, page, size);
        return ResponseEntity.ok(SuccessResponse.ok(response));
    }
}
