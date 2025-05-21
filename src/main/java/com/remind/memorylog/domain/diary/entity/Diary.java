package com.remind.memorylog.domain.diary.entity;

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
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId; // 기록 ID

    private String song; // 노래

    private String imageUrl; // 이미지 URL

    private String content; // 본문

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false)
    private Member member; // 외래키(memberId)

}
