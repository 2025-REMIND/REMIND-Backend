package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.remind.memorylog.global.constant.StaticValue.FORBIDDEN;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseResponseCode {
    MEMBER_ALREADY_EXIST_409("SIGNUP_409_1", 409, "이미 존재하는 회원입니다."),
    MEMBER_ID_PASSWORD_SAME("SIGNUP_400_6", 400, "아이디와 비밀번호는 동일할 수 없습니다."),
    MEMBER_NOT_FOUND("SIGNIN_400_1", 400, "존재하지 않는 사용자입니다."),
    PASSWORD_MISMATCH("SIGNIN_400_2", 400, "비밀번호가 일치하지 않습니다."),
    CAN_NOT_ACCESS_403("CAN_NOT_ACCESS_403", FORBIDDEN, "접근 권한이 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;

}
