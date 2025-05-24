package com.remind.memorylog.domain.course.service;

import com.remind.memorylog.domain.course.web.dto.GetOneCourseRes;

public interface CourseService {
    void createCourse(Long memberId);
    GetOneCourseRes getOneCourse(Long memberId, Long courseId);
}
