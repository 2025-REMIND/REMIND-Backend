package com.remind.memorylog.domain.chat.web.controller;

import com.remind.memorylog.domain.chat.service.ChatService;
import com.remind.memorylog.domain.chat.web.dto.SendChatReq;
import com.remind.memorylog.domain.chat.web.dto.SendChatRes;
import com.remind.memorylog.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<SuccessResponse<SendChatRes>> sendMessage(@Valid @RequestBody SendChatReq sendChatReq) {
        SendChatRes sendChatRes = chatService.sendChat(sendChatReq);
        return ResponseEntity.ok(SuccessResponse.ok(sendChatRes));
    }
}
