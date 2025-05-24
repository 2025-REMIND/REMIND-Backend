package com.remind.memorylog.domain.course.exception;

import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.remind.memorylog.global.constant.StaticValue.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum CourseErrorCode implements BaseResponseCode {
    COURSE_NOT_FOUND_404("COURSE_NOT_FOUND_404", NOT_FOUND,"존재하지 않는 데이트 코스입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
