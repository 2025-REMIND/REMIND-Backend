package com.remind.memorylog.domain.course.exception;

import com.remind.memorylog.global.exception.BaseException;

public class CourseDetailNotFoundException extends BaseException {
    public CourseDetailNotFoundException() {
        super(CourseErrorCode.COURSE_DETAIL_NOT_FOUND_404);
    }
}
