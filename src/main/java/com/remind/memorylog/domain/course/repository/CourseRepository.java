package com.remind.memorylog.domain.course.repository;

import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.course.exception.CourseNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findCourseByIdAndCreatedAtBetween(Long id, LocalDateTime start, LocalDateTime end);

    default Course getCourseById(Long courseId) {
        return findById(courseId).orElseThrow(CourseNotFoundException::new);
    }

}
