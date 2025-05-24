package com.remind.memorylog.domain.course.web.controller;

import com.remind.memorylog.domain.course.service.CourseService;
import com.remind.memorylog.domain.course.web.dto.GetCourseDetailRes;
import com.remind.memorylog.domain.course.web.dto.GetOneCourseRes;
import com.remind.memorylog.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{memberId}/{courseId}")
    public ResponseEntity<SuccessResponse<GetOneCourseRes>> getOneCourse(
            @PathVariable("memberId") Long memberId,
            @PathVariable("courseId") Long courseId) {
        GetOneCourseRes getOneCourseRes = courseService.getOneCourse(memberId, courseId);

        return ResponseEntity.ok(SuccessResponse.ok(getOneCourseRes));
    }
}
