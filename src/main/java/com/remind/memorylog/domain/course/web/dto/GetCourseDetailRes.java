package com.remind.memorylog.domain.course.web.dto;

import com.remind.memorylog.domain.course.entity.CourseDetail;
import com.remind.memorylog.domain.course.entity.enums.Status;

public record GetCourseDetailRes(
        Long courseDetailId,
        String content,
        Status status) {
    public static GetCourseDetailRes of(CourseDetail courseDetail) {
        return new GetCourseDetailRes(courseDetail.getId(), courseDetail.getContent(), courseDetail.getStatus());
    }
}
