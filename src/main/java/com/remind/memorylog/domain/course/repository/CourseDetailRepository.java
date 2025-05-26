package com.remind.memorylog.domain.course.repository;

import com.remind.memorylog.domain.course.entity.CourseDetail;
import com.remind.memorylog.domain.course.exception.CourseDetailNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDetailRepository extends JpaRepository<CourseDetail, Long> {
    List<CourseDetail> findByCourseId(Long courseId);

    default CourseDetail getByCourseDetailId(Long courseDetailId) {
        return findById(courseDetailId).orElseThrow(CourseDetailNotFoundException::new);
    }
}
