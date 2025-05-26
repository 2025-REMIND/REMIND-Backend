package com.remind.memorylog.domain.mission.web.dto;

import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.mission.entity.Mission;
import com.remind.memorylog.global.entity.enums.Status;

public record GetMissionRes(
        Long courseId,
        String title,
        String description,
        Status status
) {
    public static GetMissionRes from(Mission mission) {
        return new GetMissionRes(mission.getId(), mission.getTitle(), mission.getDescription(), mission.getStatus());
    }
}
