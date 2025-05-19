package com.remind.memorylog.global.exception;

import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 역할 : 여러 도메인에서 다양한 예외 상황이 발생할 때, 에러 코드와 함께 일관성 있는 방식으로 예외를 던지고 처리할 수 있게 해줌
// 주로 GlobalExceptionHandler 같은 전역 예외 처리기에서 잡혀서 사용자에게 통일된 에러 응답을 반환하는데 활용됨
@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException { // RuntimeException을 상속받아, 예외 발생 시 try-catch 없이도 처리 가능하도록 함
    public final BaseResponseCode errorCode; // 이 필드를 통해 에러의 원인이나 종류를 코드 형태로 명확하게 표현할 수 있게 해줌
}
