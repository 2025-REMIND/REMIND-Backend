package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class MemberAlreadyExistException extends BaseException {
    public MemberAlreadyExistException() {
        super(MemberErrorCode.MEMBER_ALREADY_EXIST_409);
    }
}
