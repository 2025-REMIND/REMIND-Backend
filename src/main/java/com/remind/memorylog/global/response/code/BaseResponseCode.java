package com.remind.memorylog.global.response.code;

// 역할 : 에러/성공 응답 코드를 위한 공통 인터페이스
// enum 타입으로 에러/성공 코드를 정의할 때 유용하게 쓰임
public interface BaseResponseCode {
    String getCode(); // 에러 코드 (ex E4001, S2001)
    String getMessage(); // 사용자에게 보여줄 메시지 (ex 요청이 잘못되었습니다, 성공적으로 처리되었습니다)
    int getHttpStatus(); // HTTP 상태 코드 (ex 400, 404, 200)
}
