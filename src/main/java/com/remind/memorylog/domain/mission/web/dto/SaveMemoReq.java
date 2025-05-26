package com.remind.memorylog.domain.mission.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveMemoReq(
        @NotNull(message = "사용자 기본키는 필수입니다.")
        Long memberId,

        @NotNull(message = "미션 디테일 ID 1은 필수입니다.")
        Long missionDetailId1,

        @NotNull(message = "메모1은 필수입니다.")
        String memo1,

        @NotNull(message = "미션 디테일 ID 2는 필수입니다.")
        Long missionDetailId2,

        @NotBlank(message = "메모2는 필수입니다.")
        String memo2,

        @NotNull(message = "미션 디테일 ID 3은 필수입니다.")
        Long missionDetailId3,

        @NotBlank(message = "메모3은 필수입니다.")
        String memo3
) {
}

