package com.remind.memorylog.global.external.exception;


import com.remind.memorylog.global.exception.BaseException;

public class AiResultParsingException extends BaseException {
    public AiResultParsingException() {super(ExternalErrorCode.OPENAI_PARSING_ERROR_500);}
}
