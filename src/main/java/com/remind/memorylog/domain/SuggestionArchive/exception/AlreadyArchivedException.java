package com.remind.memorylog.domain.SuggestionArchive.exception;

import com.remind.memorylog.global.exception.BaseException;

public class AlreadyArchivedException extends BaseException {
    public AlreadyArchivedException() {
        super(SuggestionArchiveErrorCode.ALREADY_ARCHIVED);
    }
}
