package com.remind.memorylog.domain.SuggestionImage.service;

import com.remind.memorylog.domain.SuggestionImage.web.dto.SuggestionImageUploadResponse;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordRequest;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SuggestionImageService {
    SuggestionImageUploadResponse uploadImages(Long suggestionId, List<MultipartFile> images) throws IOException;
}
