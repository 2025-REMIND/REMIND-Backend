package com.remind.memorylog.domain.chat.web.dto;

import com.remind.memorylog.domain.chat.entity.Chat;
import com.remind.memorylog.domain.chat.entity.enums.Sender;

public record SendChatRes(
        String content
) {
    public static SendChatRes of(Chat chat){
        return new SendChatRes(chat.getContent());
    }
}
