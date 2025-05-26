package com.remind.memorylog.domain.course.web.controller;

import com.remind.memorylog.domain.course.service.CourseService;
import com.remind.memorylog.domain.course.web.dto.*;
import com.remind.memorylog.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    // 오늘의 코스 조회
    @GetMapping("/{memberId}/today")
    public ResponseEntity<SuccessResponse<GetCourseRes>> getTodayCourse(@PathVariable Long memberId) {
        GetCourseRes getCourseRes = courseService.getCourse(memberId);
        return ResponseEntity.ok(SuccessResponse.ok(getCourseRes));
    }

    // 코스 하나 상세 조회
    @GetMapping("/{memberId}/{courseId}")
    public ResponseEntity<SuccessResponse<GetOneCourseRes>> getOneCourse(
            @PathVariable("memberId") Long memberId,
            @PathVariable("courseId") Long courseId) {
        GetOneCourseRes getOneCourseRes = courseService.getOneCourse(memberId, courseId);
        return ResponseEntity.ok(SuccessResponse.ok(getOneCourseRes));
    }

    // 코스 상태 변경
    @PutMapping("/{courseDetailId}")
    public ResponseEntity<SuccessResponse<?>> updateCourseStatus(
            @PathVariable("courseDetailId") Long courseDetailId,
            @RequestBody UpdateCourseReq updateCourseReq) {
        courseService.updateCourseDetailStatus(courseDetailId, updateCourseReq);
        return ResponseEntity.ok(SuccessResponse.empty());
    }
}
