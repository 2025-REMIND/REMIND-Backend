package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class InvalidIdLengthException extends BaseException {
    public InvalidIdLengthException() {
        super(MemberErrorCode.INVALID_ID_LENGTH);
    }
}
