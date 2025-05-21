package com.remind.memorylog.domain.diary.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiaryRecordRequest {
    @NotNull(message = "회원 Id를 함께 보내주세요")
    private Long memberId;

    private String content; // 본문

    private String song; // 노래

}