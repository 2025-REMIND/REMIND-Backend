package com.remind.memorylog.domain.course.web.dto;

import com.remind.memorylog.domain.course.entity.Course;
import java.util.List;

public record GetOneCourseRes( // 추천 데이트 코스 상세 조회
        String place,
        String description,
        String time,
        List<GetCourseDetailRes> getCourseDetailResList) {
    public static GetOneCourseRes of(Course  course, List<GetCourseDetailRes> getCourseDetailResList) {
        return new GetOneCourseRes(course.getPlace(), course.getDescription(),course.getTime(), getCourseDetailResList);
    }
}
