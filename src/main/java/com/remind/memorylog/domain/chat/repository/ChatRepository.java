package com.remind.memorylog.domain.chat.repository;

import com.remind.memorylog.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByMember_MemberId(Long memberId);
}
