package com.remind.memorylog.global.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.remind.memorylog.global.constant.StaticValue.*;

// 역할 : 서버에서 클라이언트에게 성공적인 요청 처리 결과를 반환할 때 사용하는, 공통 성공 응답 코드 집합이다.
@Getter
@AllArgsConstructor
public enum GlobalSuccessCode implements BaseResponseCode {
    SUCCESS_OK("SUCCESS_200", OK, "호출에 성공하였습니다."), // 일반적인 조회 성공
    SUCCESS_CREATED("CREATED_201", CREATED, "호출에 성공하였습니다."); // 리소스 생성 성공

    private final String code;
    private final int httpStatus;
    private final String message;
}
