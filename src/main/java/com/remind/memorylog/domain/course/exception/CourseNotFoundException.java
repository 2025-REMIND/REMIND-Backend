package com.remind.memorylog.domain.course.exception;

import com.remind.memorylog.global.exception.BaseException;

public class CourseNotFoundException extends BaseException {
    public CourseNotFoundException() {
        super(CourseErrorCode.COURSE_NOT_FOUND_404);
    }
}
