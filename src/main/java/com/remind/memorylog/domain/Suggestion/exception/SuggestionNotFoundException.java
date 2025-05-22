package com.remind.memorylog.domain.Suggestion.exception;

import com.remind.memorylog.global.exception.BaseException;

public class SuggestionNotFoundException extends BaseException {
    public SuggestionNotFoundException() {
        super(SuggestionErrorCode.SUGGESTION_NOT_FOUND);
    }
}
