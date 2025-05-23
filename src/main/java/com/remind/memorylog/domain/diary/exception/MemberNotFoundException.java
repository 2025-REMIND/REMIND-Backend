package com.remind.memorylog.domain.diary.exception;

import com.remind.memorylog.global.exception.BaseException;

public class MemberNotFoundException extends BaseException {
    public MemberNotFoundException() {
        super(DiaryErrorCode.MEMBER_NOT_FOUND);
    }
}
