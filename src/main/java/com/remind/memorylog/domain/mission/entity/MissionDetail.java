package com.remind.memorylog.domain.mission.entity;

import com.remind.memorylog.global.entity.BaseEntity;
import com.remind.memorylog.global.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mission_detail")
public class MissionDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    private String content;

    private String memo;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void updateStatus() {
        this.status = (this.status == Status.PROGRESS) ? Status.DONE : Status.PROGRESS;
    }
}
