package com.remind.memorylog.domain.course.web.dto;

import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.global.entity.enums.Status;

public record GetCourseRes(
        Long courseId,
        String title,
        String description,
        Status status
) {
    public static GetCourseRes from(Course course) {
        return new GetCourseRes(course.getId(), course.getTitle(), course.getDescription(), course.getStatus());
    }
}
