package com.remind.memorylog.global.external.exception;


import com.remind.memorylog.global.exception.BaseException;

public class OpenAiApiException extends BaseException {
    public OpenAiApiException() {super(ExternalErrorCode.OPENAI_REQUEST_ERROR_500);}
}
