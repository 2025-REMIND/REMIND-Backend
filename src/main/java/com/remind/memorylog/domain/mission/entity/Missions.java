package com.remind.memorylog.domain.mission.entity;

import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Missions extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggestion_id")
    private Suggestion suggestion;

    private String mission1;
    private Boolean checked1;

    private String mission2;
    private Boolean checked2;

    private String mission3;
    private Boolean checked3;

    @Enumerated(EnumType.STRING)
    private MissionsStatus status;


}
