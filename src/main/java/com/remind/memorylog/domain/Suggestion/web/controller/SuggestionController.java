package com.remind.memorylog.domain.Suggestion.web.controller;

import com.remind.memorylog.domain.Suggestion.repository.SuggestionRepository;
import com.remind.memorylog.domain.Suggestion.service.SuggestionService;
import com.remind.memorylog.domain.Suggestion.web.dto.SuggestionArchiveResponse;
import com.remind.memorylog.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionController { }
