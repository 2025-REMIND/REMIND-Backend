package com.remind.memorylog.domain.course.repository;

import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.course.exception.CourseNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByMember_MemberIdAndCreatedAtBetween(Long memberId, LocalDateTime start, LocalDateTime end);

    default Course getCourseById(Long courseId) {
        return findById(courseId).orElseThrow(CourseNotFoundException::new);
    }

}
