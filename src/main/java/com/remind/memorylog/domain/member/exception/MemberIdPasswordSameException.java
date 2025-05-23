package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class MemberIdPasswordSameException extends BaseException {
    public MemberIdPasswordSameException() {
        super(MemberErrorCode.MEMBER_ID_PASSWORD_SAME);
    }
}
