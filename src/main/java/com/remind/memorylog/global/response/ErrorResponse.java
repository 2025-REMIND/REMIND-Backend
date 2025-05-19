package com.remind.memorylog.global.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter // httpStatus와 data 필드에 대해 자동으로 getter 메서드 (getHttpStatus(), getData())를 생성해줌
@ToString // 객체 내용을 문자열로 출력할 수 있도록 toString() 메서드를 자동 생성
@JsonPropertyOrder({"isSuccess", "timestamp", "code", "httpStatus", "message", "data"}) // JSON 응답의 필드 순서를 강제로 지정, 이렇게 하면 프론트엔드가 항상 예측 가능한 형태의 응답을 받을 수 있음
public class ErrorResponse<T> extends BaseResponse {
    private final int httpStatus; // HTTP 상태 코드 (ex 400, 404, 500), 에러의 HTTP 레벨을 명시
    private final T data; // 에러 상세정보를 담을 수 있는 필드 (ex 에러 리스트, 필드 오류 정보 등)

    //생성자
    @Builder
    public ErrorResponse(T data, String code, String message, int httpStatus) {
        super(false, code, message);
        this.data = data;
        this.httpStatus = httpStatus;
    }


    // 기본 메시지를 그대로 사용할 떄
    public static ErrorResponse<?> of(BaseResponseCode baseCode) {
        return ErrorResponse.builder()
                .code(baseCode.getCode()) // 에러 코드 값 (ex GLOBAL_400_1) 설정
                .message(baseCode.getMessage()) // 에러 메시지 (ex 잘못된 요청입니다) 설정
                .httpStatus(baseCode.getHttpStatus()) // HTTP 상태 코드 (ex 400, 404)
                .data(null) // data는 필요 없으니 null로 설정
                .build(); // 위 값들로 ErrorResponse 객체 완성 후 반환
    }

    // 메시지를 직접 설정하고 싶을 때 사용
    public static <T> ErrorResponse<T> of(BaseResponseCode baseCode, String message) {
        return ErrorResponse.<T>builder()
                .code(baseCode.getCode()) // 고정된 에러 코드 사용
                .httpStatus(baseCode.getHttpStatus()) // 상태 코드도 에러 코드에서 가져옴
                .message(message) // 개발자가 직접 전달한 커스텀 메시지 사용
                .data(null) // 추가 데이터는 없으므로 null
                .build(); // 최종 객체 생성 후 반환
    }

    // 커스텀 메시지 + 추가 데이터까지 포함하고 싶을 때
    public static <T> ErrorResponse<T> of(BaseResponseCode baseCode, String message, T data) {
        return ErrorResponse.<T>builder()
                .code(baseCode.getCode())
                .httpStatus(baseCode.getHttpStatus())
                .message(message) // 메시지는 호출자가 원하는 대로 설정 가능
                .data(data) // 실제 데이터를 포함시킬 수 있음
                .build(); // 완성된 ErrorResponse<T>
    }
}
