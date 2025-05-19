package com.remind.memorylog.domain.member.exception;

import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseResponseCode {


    INVALID_ID_PATTERN("SIGNUP_400_1", 400, "영문과 숫자를 조합해주세요."),
    INVALID_ID_LENGTH("SIGNUP_400_2", 400, "4자~10자 이내로 작성해주세요."),
    DUPLICATED_ID("SIGNUP_400_3", 400, "아이디가 중복됩니다."),
    INVALID_PASSWORD_PATTERN("SIGNUP_400_4", 400, "영문과 숫자를 조합해주세요."),
    INVALID_PASSWORD_LENGTH("SIGNUP_400_5", 400, "4자~10자 이내로 작성해주세요."),
    INVALID_ID_PASSWORD_SAME("SIGNUP_400_6", 400, "아이디와 비밀번호는 동일할 수 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;

}
