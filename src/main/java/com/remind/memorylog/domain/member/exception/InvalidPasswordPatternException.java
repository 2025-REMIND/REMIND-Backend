package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class InvalidPasswordPatternException extends BaseException {
    public InvalidPasswordPatternException() {
        super(MemberErrorCode.INVALID_PASSWORD_PATTERN);
    }
}
