package com.remind.memorylog.domain.course.service;

import com.remind.memorylog.domain.course.entity.Course;

public interface CourseService {
    void createCourse(Course course, Long memberId);
}
