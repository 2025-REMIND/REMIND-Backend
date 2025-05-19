package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class InvalidIdPasswordSameException extends BaseException {
    public InvalidIdPasswordSameException() {
        super(MemberErrorCode.INVALID_ID_PASSWORD_SAME);
    }
}
