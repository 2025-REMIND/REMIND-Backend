package com.remind.memorylog.domain.SuggestionArchive.exception;


import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuggestionArchiveErrorCode implements BaseResponseCode {

    ALREADY_ARCHIVED("ARCHIVE_400_1", 400, "이미 보관된 제안입니다."),
    ARCHIVE_NOT_FOUND("ARCHIVE_404_1", 404, "보관된 제안이 존재하지 않습니다.");


    private final String code;
    private final int httpStatus;
    private final String message;
}
