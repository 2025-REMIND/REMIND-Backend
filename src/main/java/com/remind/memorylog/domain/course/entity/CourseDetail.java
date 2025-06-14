package com.remind.memorylog.domain.course.entity;

import com.remind.memorylog.global.entity.enums.Status;
import com.remind.memorylog.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "course_detail")
public class CourseDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private String content;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void updateStatus() {
        this.status = (this.status == Status.PROGRESS) ? Status.DONE : Status.PROGRESS;
    }

}
