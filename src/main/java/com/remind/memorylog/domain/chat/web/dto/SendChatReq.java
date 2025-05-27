package com.remind.memorylog.domain.chat.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record SendChatReq(
        @NotNull(message = "사용자 기본키는 필수입니다.")
        Long memberId,

        @NotBlank(message = "사용자 메시지는 필수입니다.")
        String content
) {
}
