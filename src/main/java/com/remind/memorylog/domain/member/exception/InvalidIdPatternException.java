package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class InvalidIdPatternException extends BaseException {
    public InvalidIdPatternException() {
        super(MemberErrorCode.INVALID_ID_PATTERN);
    }
}
