package com.remind.memorylog.domain.diary.service;

import com.remind.memorylog.domain.diary.web.dto.DiaryRecordRequest;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DiaryService {
    DiaryRecordResponse recordMemory(DiaryRecordRequest diaryRequest, MultipartFile image) throws IOException;
    List<DiaryRecordResponse> getRecentDiaries(Long memberId);
}
