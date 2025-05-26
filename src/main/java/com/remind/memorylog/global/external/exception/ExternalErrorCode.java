package com.remind.memorylog.global.external.exception;

import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.remind.memorylog.global.constant.StaticValue.INTERNAL_SERVER_ERROR;

@Getter
@AllArgsConstructor
public enum ExternalErrorCode implements BaseResponseCode {
    // open ai
    IMAGE_ENCODE_ERROR_500("IMAGE_ENCODE_ERROR_500", INTERNAL_SERVER_ERROR, "파일 인코딩 중 에러가 발생했습니다."),
    OPENAI_REQUEST_ERROR_500("OPENAI_REQUEST_ERROR_500", INTERNAL_SERVER_ERROR, "OpenAI 호출 중 에러가 발생했습니다."),
    OPENAI_PARSING_ERROR_500("OPENAI_PARSING_ERROR_500", INTERNAL_SERVER_ERROR, "OpenAI 응답 파싱 중 에러가 발생했습니다.");


    private final String code;
    private final int httpStatus;
    private final String message;
}
