package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class InvalidPasswordLengthException extends BaseException {
    public InvalidPasswordLengthException() {
        super(MemberErrorCode.INVALID_PASSWORD_LENGTH);
    }
}
