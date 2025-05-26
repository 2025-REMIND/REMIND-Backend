package com.remind.memorylog.domain.mission.exception;

import com.remind.memorylog.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.remind.memorylog.global.constant.StaticValue.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseResponseCode {
    MISSION_NOT_FOUND_404("MISSION_NOT_FOUND_404", NOT_FOUND,"존재하지 않는 미션입니다."),
    MISSION_DETAIL_NOT_FOUND_404("MISSION_DETAIL_NOT_FOUND_404", NOT_FOUND,"존재하지 않는 미션 내역입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
