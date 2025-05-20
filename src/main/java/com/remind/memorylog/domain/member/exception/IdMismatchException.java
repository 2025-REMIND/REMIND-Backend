package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class IdMismatchException extends BaseException {
    public IdMismatchException() {
        super(MemberErrorCode.ID_MISMATCH);
    }
}
