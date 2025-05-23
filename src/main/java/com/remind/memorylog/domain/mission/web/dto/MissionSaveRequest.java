package com.remind.memorylog.domain.mission.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MissionSaveRequest {

    String answer1;
    Boolean checked1;

    String answer2;
    Boolean checked2;

    String answer3;
    Boolean checked3;
}
