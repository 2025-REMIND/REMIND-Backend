package com.remind.memorylog.domain.course.service;

import com.remind.memorylog.domain.course.web.dto.GetCourseRes;
import com.remind.memorylog.domain.course.web.dto.GetOneCourseRes;
import com.remind.memorylog.domain.course.web.dto.UpdateCourseReq;

public interface CourseService {
    void createCourse(Long memberId);
    GetOneCourseRes getOneCourse(Long memberId, Long courseId);
    GetCourseRes getCourse(Long memberId);

    void updateCourseDetailStatus(Long courseDetailId, UpdateCourseReq updateCourseReq);
}
