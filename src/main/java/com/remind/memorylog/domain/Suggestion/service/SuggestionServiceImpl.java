package com.remind.memorylog.domain.Suggestion.service;

import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.domain.Suggestion.entity.SuggestionArchiveStatus;
import com.remind.memorylog.domain.Suggestion.exception.SuggestionNotFoundException;
import com.remind.memorylog.domain.Suggestion.repository.SuggestionRepository;
import com.remind.memorylog.domain.Suggestion.web.dto.SuggestionArchiveResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;

    // 제안을 일정 보관함에 저장
   @Transactional
    @Override
    public SuggestionArchiveResponse archiveSuggestion(Long suggestionId) {
       // 제안 존재 확인
       Suggestion suggestion = suggestionRepository.findById(suggestionId)
               .orElseThrow(SuggestionNotFoundException::new);

       suggestion.setArchiveStatus(SuggestionArchiveStatus.ARCHIVED);
       return new SuggestionArchiveResponse(
               suggestion.getSuggestionId()
       );
    }
}
