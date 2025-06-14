package com.remind.memorylog.domain.chat.entity;

import com.remind.memorylog.domain.chat.entity.enums.Sender;
import com.remind.memorylog.domain.chat.web.dto.SendChatReq;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat")
public class Chat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @Enumerated(EnumType.STRING)
    private Sender sender;

    public static Chat toEntity(String content, Member member, Sender sender) {
        return Chat.builder()
                .content(content)
                .member(member)
                .sender(sender)
                .build();
    }
}
