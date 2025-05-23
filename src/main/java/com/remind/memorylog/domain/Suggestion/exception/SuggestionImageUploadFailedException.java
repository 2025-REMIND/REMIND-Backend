package com.remind.memorylog.domain.Suggestion.exception;

import com.remind.memorylog.global.exception.BaseException;

public class SuggestionImageUploadFailedException extends BaseException {
    public SuggestionImageUploadFailedException() {
        super(SuggestionErrorCode.IMAGE_UPLOAD_FAILED);
    }
}
