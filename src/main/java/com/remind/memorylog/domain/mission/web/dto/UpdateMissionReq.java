package com.remind.memorylog.domain.mission.web.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateMissionReq(
        @NotNull(message = "사용자 기본키는 필수입니다.")
        Long memberId
) {}
