package com.remind.memorylog.domain.member.entity;

import com.remind.memorylog.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId; // 회원 고유 ID

    private String loginId; // 아이디

    private String loginPwd; // 비밀번호

    private String name; // 닉네임 (리미 대화 이후로는 사용자 이름으로 변경)
}
