package com.remind.memorylog.domain.course.entity;

import com.remind.memorylog.domain.course.entity.enums.Status;
import com.remind.memorylog.domain.course.web.dto.CreateCourseReq;
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

    private String title;

    private String place;

    private String description;

    private String time;

    @Enumerated(EnumType.STRING)
    private Status status;

    // courseDetail도 같이 생성 및 삭제
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseDetail> courseDetails = new ArrayList<>();

    public void addCourseDetail(CourseDetail courseDetail) {
        this.courseDetails.add(courseDetail);
        courseDetail.setCourse(this);
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public static Course toEntity(CreateCourseReq req, Member member) {
        Course course = Course.builder()
                .title(req.title())
                .place(req.place())
                .description(req.description())
                .time(req.time())
                .status(Status.PROGRESS) // 기본 상태는 진행 중
                .courseDetails(new ArrayList<>())
                .member(member)
                .build();

        // 미션 목록을 CourseDetail로 만들어서 연결
        List<String> missions = List.of(req.mission1(), req.mission2(), req.mission3());
        for (String mission : missions) {
            CourseDetail detail = CourseDetail.builder()
                    .content(mission)
                    .status(Status.PROGRESS) // 기본 상태는 진행 중
                    .build();

            course.addCourseDetail(detail); // 연관관계 설정
        }

        return course;
    }


}
