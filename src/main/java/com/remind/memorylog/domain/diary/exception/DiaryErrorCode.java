package com.remind.memorylog.domain.diary.exception;

import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiaryErrorCode implements BaseResponseCode {

    IMAGE_UPLOAD_FAILED("DIARY_500_1", 500, "이미지 업로드에 실패했습니다."),
    MEMBER_NOT_FOUND("DIARY_404_1", 404, "해당 회원을 찾을 수 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
