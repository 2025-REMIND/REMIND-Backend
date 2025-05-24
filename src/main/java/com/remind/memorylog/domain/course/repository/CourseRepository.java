package com.remind.memorylog.domain.course.repository;

import com.remind.memorylog.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findCourseByIdAndCreatedAtBetween(Long id, LocalDateTime start, LocalDateTime end);
}
