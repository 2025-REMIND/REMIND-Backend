package com.remind.memorylog.domain.chat.web.dto;

import com.remind.memorylog.domain.chat.entity.Chat;
import com.remind.memorylog.domain.chat.entity.enums.Sender;

public record GetChatRes(
        String content,
        Sender sender
) {
    public static GetChatRes of(Chat chat){
        return new GetChatRes(chat.getContent(), chat.getSender());
    }
}
