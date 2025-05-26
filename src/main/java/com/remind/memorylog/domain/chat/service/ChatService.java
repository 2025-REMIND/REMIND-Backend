package com.remind.memorylog.domain.chat.service;

import com.remind.memorylog.domain.chat.web.dto.SendChatReq;
import com.remind.memorylog.domain.chat.web.dto.SendChatRes;

public interface ChatService {
    SendChatRes sendChat(SendChatReq sendChatReq);
}
