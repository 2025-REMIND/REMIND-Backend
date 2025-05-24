package com.remind.memorylog.domain.SuggestionArchive.exception;

import com.remind.memorylog.global.exception.BaseException;

public class SuggestionArchiveNotFoundException extends BaseException {
    public SuggestionArchiveNotFoundException() {
        super(SuggestionArchiveErrorCode.ARCHIVE_NOT_FOUND);
    }
}
