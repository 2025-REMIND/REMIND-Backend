package com.remind.memorylog.global.response;

import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@RequiredArgsConstructor
public class BaseResponse {
    private final Boolean isSuccess; // 이 요청이 성공했는지 실패했는지를 나타내는 플래그

    private final String code; // 에러나 성공에 대한 고유 식별자 (ex SUCCESS_200, GLOBAL_400_1)

    private final String message; // 사용자에게 보여줄 메시지 (ex 요청이 성공했습니다, 입력값이 유효하지 않습니다.)

    // 응답 생성 시점의 시간을 기록한 필드
    private final String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    // BaseResponseCode에서 정의된 코드와 메시지를 그대로 사용해서 응답 생성 (지정된 응답 코드에 따라, 정해진 메시지 그대로 보여줄 때)
    public static BaseResponse of(Boolean isSuccess, BaseResponseCode baseCode) {
        return new BaseResponse(isSuccess, baseCode.getCode(), baseCode.getMessage());
    }

    // 응답 코드 값(baseCode.getCode())은 유지, 메시지(baseCode.getMessage())는 무시하고 커스터마이징된 메시지를 넣을 수 있음
    public static BaseResponse of(Boolean isSuccess, BaseResponseCode baseCode, String message) {
        return new BaseResponse(isSuccess, baseCode.getCode(), message);
    }

    // BaseResponseCode 같은 공통 코드 없이도 사용 가능, 완전한 자유형 응답을 만들고 싶을 때 사용
    public static BaseResponse of(Boolean isSuccess, String code, String message) {
        return new BaseResponse(isSuccess, code, message);
    }
}
