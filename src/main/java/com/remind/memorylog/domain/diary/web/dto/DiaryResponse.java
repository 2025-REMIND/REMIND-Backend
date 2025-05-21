package com.remind.memorylog.domain.diary.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryResponse {
    private Long diaryId;
    private Long memberId;
    private String content;
    private String song;
    private String imageUrl;
}
