package com.remind.memorylog.global.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.remind.memorylog.global.response.code.BaseResponseCode;
import com.remind.memorylog.global.response.code.GlobalSuccessCode;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonPropertyOrder({"isSuccess", "timestamp", "code", "httpStatus", "message", "data"})
public class SuccessResponse<T> extends BaseResponse{
    private final int httpStatus; // HTTP 상태 코드 (ex 200, 201)
    private final T data; // 실제 반환할 데이터


    @Builder
    public SuccessResponse(T data, BaseResponseCode baseResponseCode) {
        super(true, baseResponseCode.getCode(), baseResponseCode.getMessage());
        this.data = data;
        this.httpStatus = baseResponseCode.getHttpStatus();
    }

    // 200 OK 응답
    public static <T> SuccessResponse<T> ok(T data) {
        return new SuccessResponse<>(data, GlobalSuccessCode.SUCCESS_OK);
    }

    // 201 Created 응답
    public static <T> SuccessResponse<T> created(T data) {
        return new SuccessResponse<>(data, GlobalSuccessCode.SUCCESS_CREATED);
    }

    // 데이터가 없을 때, null을 응답으로 보내는 전용 응답
    public static <T> SuccessResponse<T> empty() {
        return new SuccessResponse<>(null, GlobalSuccessCode.SUCCESS_OK);
    }

    // 응답 코드와 메시지를 직접 지정해서 유연하게 성공 응답을 만들 수 있게 함
    public static <T> SuccessResponse<T> of(T data, BaseResponseCode baseResponseCode) {
        return new SuccessResponse<>(data, baseResponseCode);
    }

}