package com.remind.memorylog.domain.Suggestion.exception;

import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuggestionErrorCode implements BaseResponseCode {

    IMAGE_UPLOAD_FAILED("SUGGESTION_500_1", 500, "이미지 업로드에 실패했습니다."),
    SUGGESTION_NOT_FOUND("SUGGESTION_404_1", 404, "해당 제안이 존재하지 않습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
