package com.remind.memorylog.domain.course.web.dto;

public record CreateCourseReq(
        String title,
        String place,
        String description,
        String time,
        String mission1,
        String mission2,
        String mission3) {

}
