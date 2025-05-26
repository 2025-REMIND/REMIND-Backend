package com.remind.memorylog.domain.mission.web.dto;

import com.remind.memorylog.domain.mission.entity.MissionDetail;
import com.remind.memorylog.global.entity.enums.Status;

public record GetMissionDetailRes(
        Long missionDetailId,
        String content,
        String memo,
        Status status) {
    public static GetMissionDetailRes of(MissionDetail missionDetail) {
        return new GetMissionDetailRes(missionDetail.getId(), missionDetail.getContent(), missionDetail.getMemo(), missionDetail.getStatus());
    }
}
