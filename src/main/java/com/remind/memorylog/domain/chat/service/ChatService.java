package com.remind.memorylog.domain.chat.service;

import com.remind.memorylog.domain.chat.web.dto.GetChatRes;

import java.util.List;

public interface ChatService {
    List<GetChatRes> getChatList(Long memberId);
}
