package com.remind.memorylog.domain.diary.service;

import com.remind.memorylog.domain.diary.web.dto.DiaryRequest;

public interface DiaryService {
    void record(DiaryRequest diaryRequest, String imageUrl);
}
