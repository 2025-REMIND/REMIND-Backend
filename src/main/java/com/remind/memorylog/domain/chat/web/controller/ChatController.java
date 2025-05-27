package com.remind.memorylog.domain.chat.web.controller;

import com.remind.memorylog.domain.chat.service.ChatService;
import com.remind.memorylog.domain.chat.web.dto.SendChatReq;
import com.remind.memorylog.domain.chat.web.dto.SendChatRes;
import com.remind.memorylog.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<SendChatRes>> sendMessage(
            @Valid @RequestPart(value = "data") SendChatReq sendChatReq,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        SendChatRes sendChatRes = chatService.sendChat(sendChatReq, image);
        return ResponseEntity.ok(SuccessResponse.ok(sendChatRes));
    }
}
