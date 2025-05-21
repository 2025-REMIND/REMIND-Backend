package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class PasswordMismatchException extends BaseException {
    public PasswordMismatchException() {
        super(MemberErrorCode.PASSWORD_MISMATCH);
    }
}
