package com.remind.memorylog.domain.mission.web.dto;

public record CreateMissionReq(
        String title,
        String description,
        String mission1,
        String mission2,
        String mission3) {
}
