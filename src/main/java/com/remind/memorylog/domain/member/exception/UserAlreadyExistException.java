package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class UserAlreadyExistException extends BaseException {
    public UserAlreadyExistException() {
        super(MemberErrorCode.USER_ALREADY_EXIST_409);
    }
}
