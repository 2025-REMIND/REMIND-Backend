package com.remind.memorylog.domain.course.entity;

import com.remind.memorylog.domain.course.entity.enums.Status;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "course")
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String place;

    private String description;

    private String time;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "course")
    private List<CourseDetail> courseDetails = new ArrayList<>();

    public void addCourseDetail(CourseDetail courseDetail) {
        this.courseDetails.add(courseDetail);
        courseDetail.setCourse(this);
    }

}
