package com.remind.memorylog.domain.Suggestion.entity;

import com.remind.memorylog.domain.Suggestion.entity.SuggestionArchivedStatus;
import com.remind.memorylog.domain.member.entity.Member;
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

    // 오늘의 제안 성공 여부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SuggestionStatus status;

    // 보관함 저장 여부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SuggestionArchivedStatus archiveStatus;

}
