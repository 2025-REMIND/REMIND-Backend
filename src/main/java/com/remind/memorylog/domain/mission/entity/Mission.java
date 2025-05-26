package com.remind.memorylog.domain.mission.entity;

import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.course.entity.CourseDetail;
import com.remind.memorylog.domain.course.web.dto.CreateCourseReq;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.mission.web.dto.CreateMissionReq;
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
public class Mission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    // missionDetail도 같이 생성 및 삭제
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MissionDetail> missionDetails = new ArrayList<>();

    public void addMissionDetail(MissionDetail missionDetail) {
        this.missionDetails.add(missionDetail);
        missionDetail.setMission(this);
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public static Mission toEntity(CreateMissionReq req, Member member) {
        Mission mission = Mission.builder()
                .member(member)
                .title(req.title())
                .description(req.description())
                .status(Status.PROGRESS)
                .build();

        // 미션 목록을 CourseDetail로 만들어서 연결
        List<String> missions = List.of(req.mission1(), req.mission2(), req.mission3());
        for (String m : missions) {
            MissionDetail detail = MissionDetail.builder()
                    .content(m)
                    .status(Status.PROGRESS) // 기본 상태는 진행 중
                    .build();

            mission.addMissionDetail(detail); // 연관관계 설정
        }

        return mission;
    }
}
