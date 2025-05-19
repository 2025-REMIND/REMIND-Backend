package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class DuplicatedIdException extends BaseException {
    public DuplicatedIdException() {
        super(MemberErrorCode.DUPLICATED_ID);
    }
}
