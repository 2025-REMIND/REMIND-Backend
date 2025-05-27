package com.remind.memorylog.domain.chat.service;

import com.remind.memorylog.domain.chat.web.dto.SendChatReq;
import com.remind.memorylog.domain.chat.web.dto.SendChatRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ChatService {
    SendChatRes sendChat(SendChatReq sendChatReq, MultipartFile multipartFile) throws IOException;
}
