package com.remind.memorylog.domain.SuggestionArchive.service;

import com.remind.memorylog.domain.Suggestion.entity.SuggestionArchivedStatus;
import com.remind.memorylog.domain.Suggestion.exception.SuggestionNotFoundException;

import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.domain.Suggestion.repository.SuggestionRepository;
import com.remind.memorylog.domain.SuggestionArchive.entity.SuggestionArchive;
import com.remind.memorylog.domain.SuggestionArchive.exception.AlreadyArchivedException;
import com.remind.memorylog.domain.SuggestionArchive.exception.SuggestionArchiveNotFoundException;
import com.remind.memorylog.domain.SuggestionArchive.repository.SuggestionArchiveRepository;
import com.remind.memorylog.domain.SuggestionArchive.web.dto.SuggestionArchiveListResponse;
import com.remind.memorylog.domain.SuggestionArchive.web.dto.SuggestionArchiveResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestionArchiveServiceImpl implements SuggestionArchiveService {

    private final SuggestionRepository suggestionRepository;
    private final SuggestionArchiveRepository archiveRepository;

    // 제안을 일정 보관함에 저장
    @Transactional
    @Override
    public SuggestionArchiveResponse archive(Long suggestionId) {

        // 제안 존재 확인
        Suggestion suggestion = suggestionRepository.findById(suggestionId)
                .orElseThrow(SuggestionNotFoundException::new);

        // 만약 해당 제안이 이미 일정 보관함에 있다면 에러
        if (archiveRepository.existsBySuggestion(suggestion)) {
            throw new AlreadyArchivedException();
        }

        SuggestionArchive archive = SuggestionArchive.builder()
                .suggestion(suggestion)
                .build();

        archiveRepository.save(archive);

        // archiveStatus 상태 업데이트
        suggestion.setArchiveStatus(SuggestionArchivedStatus.ARCHIVED);

        return new SuggestionArchiveResponse(suggestion.getSuggestionId());
    }


    // 보관함 목록 조회
    @Transactional
    @Override
    public List<SuggestionArchiveListResponse> getArchivedSuggestions(Long memberId) {

        List<SuggestionArchive> archives = archiveRepository.findBySuggestion_Member_MemberId(memberId);

        // 보관된 제안이 없으면 에러
        if (archives.isEmpty()) {
            throw new SuggestionArchiveNotFoundException();
        }

        List<SuggestionArchiveListResponse> result = new ArrayList<>();

        for (SuggestionArchive archive : archives) {
            Suggestion suggestion = archive.getSuggestion();
            result.add(new SuggestionArchiveListResponse(
                    archive.getArchiveId(),
                    suggestion.getSuggestionId()
            ));
        }
        return result;
    }
}
