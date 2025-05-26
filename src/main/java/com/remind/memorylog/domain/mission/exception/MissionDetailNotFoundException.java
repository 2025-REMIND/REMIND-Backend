package com.remind.memorylog.domain.mission.exception;

import com.remind.memorylog.global.exception.BaseException;

public class MissionDetailNotFoundException extends BaseException {
    public MissionDetailNotFoundException() {
        super(MissionErrorCode.MISSION_DETAIL_NOT_FOUND_404);
    }
}
