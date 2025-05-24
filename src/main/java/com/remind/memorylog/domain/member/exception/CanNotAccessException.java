package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.exception.BaseException;

public class CanNotAccessException extends BaseException {
    public CanNotAccessException() { super(MemberErrorCode.CAN_NOT_ACCESS_403); }
}
