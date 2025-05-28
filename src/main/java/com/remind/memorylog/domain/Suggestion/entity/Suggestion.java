package com.remind.memorylog.domain.Suggestion.entity;

import com.remind.memorylog.domain.SuggestionImage.Entity.SuggestionImage;
import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.course.web.dto.GetCourseRes;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.mission.entity.Mission;
import com.remind.memorylog.domain.mission.web.dto.GetMissionRes;
import com.remind.memorylog.global.entity.BaseEntity;
import com.remind.memorylog.global.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "suggestion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SuggestionImage> suggestionImages = new ArrayList<>();


    public static Suggestion toEntity(Member member, Mission mission, Course course) {
        return Suggestion.builder()
                .member(member)
                .mission(mission)
                .course(course)
                .archiveStatus(ArchivedStatus.NOT_ARCHIVED) // 기본 상태는 저장이 안된 상태
                .build();
    }

    public void archiveSuggestion(){
        this.archiveStatus = ArchivedStatus.ARCHIVED;
    }
    public void unArchiveSuggestion(){
        this.archiveStatus = ArchivedStatus.NOT_ARCHIVED;
    }


}
