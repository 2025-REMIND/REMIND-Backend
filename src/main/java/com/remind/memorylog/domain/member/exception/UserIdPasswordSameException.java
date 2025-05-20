package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class UserIdPasswordSameException extends BaseException {
    public UserIdPasswordSameException() {
        super(MemberErrorCode.USER_ID_PASSWORD_SAME);
    }
}
