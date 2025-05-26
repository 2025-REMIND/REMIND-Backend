package com.remind.memorylog.domain.mission.exception;

import com.remind.memorylog.global.exception.BaseException;

public class MissionNotFoundException extends BaseException {
    public MissionNotFoundException() {
        super(MissionErrorCode.MISSION_NOT_FOUND_404);
    }
}
