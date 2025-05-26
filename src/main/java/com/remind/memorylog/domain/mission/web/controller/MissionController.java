package com.remind.memorylog.domain.mission.web.controller;

import com.remind.memorylog.domain.mission.service.MissionService;
import com.remind.memorylog.domain.mission.web.dto.GetMissionRes;
import com.remind.memorylog.domain.mission.web.dto.GetOneMissionRes;
import com.remind.memorylog.domain.mission.web.dto.SaveMemoReq;
import com.remind.memorylog.domain.mission.web.dto.UpdateMissionReq;
import com.remind.memorylog.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mission")
public class MissionController {
    private final MissionService missionService;

    // 오늘의 미션 조회
    @GetMapping("/{memberId}/today")
    public ResponseEntity<SuccessResponse<GetMissionRes>> getTodayMission(@PathVariable Long memberId) {
        GetMissionRes getMissionRes = missionService.getMission(memberId);
        return ResponseEntity.ok(SuccessResponse.ok(getMissionRes));
    }

    // 미션 상세 조회
    @GetMapping("/{memberId}/{missionId}")
    public ResponseEntity<SuccessResponse<GetOneMissionRes>> getOneMission(
            @PathVariable("memberId") Long memberId,
            @PathVariable("missionId") Long missionId) {
        GetOneMissionRes getOneMissionRes = missionService.getOneMission(memberId, missionId);
        return ResponseEntity.ok(SuccessResponse.ok(getOneMissionRes));
    }

    // 미션 디테일 상태 변경
    @PutMapping("/{missionDetailId}")
    public ResponseEntity<SuccessResponse<?>> updateMissionStatus(
            @PathVariable("missionDetailId") Long missionDetailId,
            @RequestBody UpdateMissionReq updateMissionReq) {
        missionService.updateMissionDetailStatus(missionDetailId, updateMissionReq);
        return ResponseEntity.ok(SuccessResponse.empty());
    }

    // 메모 저장
    @PostMapping("/{missionId}")
    public ResponseEntity<SuccessResponse<?>> saveMemo(
            @PathVariable("missionId") Long missionId, @RequestBody SaveMemoReq saveMemoReq) {
        missionService.saveMemo(missionId, saveMemoReq);
        return ResponseEntity.ok(SuccessResponse.empty());
    }
}
