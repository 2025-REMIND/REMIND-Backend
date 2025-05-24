package com.remind.memorylog.domain.SuggestionArchive.web.controller;

import com.remind.memorylog.domain.SuggestionArchive.web.dto.SuggestionArchiveResponse;
import com.remind.memorylog.domain.SuggestionArchive.service.SuggestionArchiveService;
import com.remind.memorylog.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionArchiveController {
    private final SuggestionArchiveService suggestionArchiveService;

    @PostMapping("/{suggestionId}/archive")
    public ResponseEntity<SuccessResponse<?>> archive(@PathVariable Long suggestionId) {
        SuggestionArchiveResponse response = suggestionArchiveService.archive(suggestionId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(response));
    }
}


