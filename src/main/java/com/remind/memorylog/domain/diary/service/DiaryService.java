package com.remind.memorylog.domain.diary.service;

import com.remind.memorylog.domain.diary.web.dto.DiaryRecordRequest;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordResponse;

public interface DiaryService {
    DiaryRecordResponse recordMemory(DiaryRecordRequest diaryRequest, String imageUrl);
}
