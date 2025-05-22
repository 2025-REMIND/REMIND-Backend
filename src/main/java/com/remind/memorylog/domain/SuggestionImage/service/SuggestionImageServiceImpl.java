package com.remind.memorylog.domain.SuggestionImage.service;

import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.domain.Suggestion.exception.SuggestionImageUploadFailedException;
import com.remind.memorylog.domain.Suggestion.exception.SuggestionNotFoundException;
import com.remind.memorylog.domain.Suggestion.repository.SuggestionRepository;
import com.remind.memorylog.domain.SuggestionImage.Entity.SuggestionImage;
import com.remind.memorylog.domain.SuggestionImage.repository.SuggestionImageRepository;
import com.remind.memorylog.domain.SuggestionImage.web.dto.SuggestionImageUploadResponse;
import com.remind.memorylog.domain.diary.service.S3Uploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuggestionImageServiceImpl implements SuggestionImageService {

    private final SuggestionRepository suggestionRepository;
    private final SuggestionImageRepository imageRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    @Override
    public SuggestionImageUploadResponse uploadImages(Long suggestionId, List<MultipartFile> images) throws IOException {

        // 제안 존재 확인
        Optional<Suggestion> optionalSuggestion = suggestionRepository.findById(suggestionId);
        if (optionalSuggestion.isEmpty()) {
            throw new SuggestionNotFoundException();
        }

        Suggestion suggestion = optionalSuggestion.get();
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            String imageUrl = null;

            try {
                if (image != null && !image.isEmpty()) {
                    imageUrl = s3Uploader.upload(image, "suggestion");
                }
            } catch (IOException e) {
                log.warn("이미지 업로드 실패: {}", e.getMessage());
                throw new SuggestionImageUploadFailedException();
            }

            // SuggestionImage Entity 생성
            if (imageUrl != null) {
                SuggestionImage imageEntity = SuggestionImage.builder()
                        .suggestion(suggestion)
                        .imageUrl(imageUrl)
                        .build();

                imageRepository.save(imageEntity);
                uploadedUrls.add(imageUrl);
            }
        }

        // 응답 반환
        return new SuggestionImageUploadResponse(
                suggestion.getSuggestionId(),
                uploadedUrls);
    }
}
