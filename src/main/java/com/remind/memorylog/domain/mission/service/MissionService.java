package com.remind.memorylog.domain.mission.service;

import com.remind.memorylog.domain.mission.web.dto.GetMissionRes;
import com.remind.memorylog.domain.mission.web.dto.GetOneMissionRes;
import com.remind.memorylog.domain.mission.web.dto.SaveMemoReq;
import com.remind.memorylog.domain.mission.web.dto.UpdateMissionReq;
import org.springframework.transaction.annotation.Transactional;

public interface MissionService {
    void createMission(Long memberId);
    GetOneMissionRes getOneMission(Long memberId, Long missionId);
    GetMissionRes getMission(Long memberId);

    @Transactional
    void saveMemo(Long missionId, SaveMemoReq saveMemoReq);

    void updateMissionDetailStatus(Long missionDetailId, UpdateMissionReq updateMissionReq);
}
