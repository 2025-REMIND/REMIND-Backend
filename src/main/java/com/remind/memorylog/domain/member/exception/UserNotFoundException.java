package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(MemberErrorCode.USER_NOT_FOUND);
    }
}
