package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseResponseCode {

    // 회원가입
    USER_ALREADY_EXIST_409("SIGNUP_409_1", 409, "아이디가 중복됩니다."),
    USER_ID_PASSWORD_SAME("SIGNUP_400_6", 400, "아이디와 비밀번호는 동일할 수 없습니다."),


    // 로그인
    USER_NOT_FOUND("SIGNIN_400_1", 400, "사용자를 찾을 수 없습니다."),
    PASSWORD_MISMATCH("SIGNIN_400_2", 400, "비밀번호가 일치하지 않습니다.");


    private final String code;
    private final int httpStatus;
    private final String message;

}
