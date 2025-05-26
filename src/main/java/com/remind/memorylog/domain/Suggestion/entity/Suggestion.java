package com.remind.memorylog.domain.Suggestion.entity;

import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.mission.entity.Mission;
import com.remind.memorylog.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Suggestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suggestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mission_id")
    private Mission mission;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="course_id")
    private Course course;

    // 보관함 저장 여부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArchivedStatus archiveStatus;

}
