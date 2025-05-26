package com.remind.memorylog.domain.mission.web.dto;

import com.remind.memorylog.domain.mission.entity.Mission;

import java.util.List;

public record GetOneMissionRes(
        Long missionId,
        List<GetMissionDetailRes> getMissionDetailResList) {
    public static GetOneMissionRes of(Mission mission, List<GetMissionDetailRes> getMissionDetailResList) {
        return new GetOneMissionRes(mission.getId(), getMissionDetailResList);
    }
}
