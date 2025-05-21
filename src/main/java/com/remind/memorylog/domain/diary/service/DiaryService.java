package com.remind.memorylog.domain.diary.service;

import com.remind.memorylog.domain.diary.web.dto.DiaryRequest;
import com.remind.memorylog.domain.diary.web.dto.DiaryResponse;

public interface DiaryService {
    DiaryResponse record(DiaryRequest diaryRequest, String imageUrl);
}
