package com.remind.memorylog.domain.chat.service;

import com.remind.memorylog.domain.chat.entity.Chat;
import com.remind.memorylog.domain.chat.repository.ChatRepository;
import com.remind.memorylog.domain.chat.web.dto.GetChatRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public List<GetChatRes> getChatList(Long memberId){
        List<Chat> chatList = chatRepository.findAllByMemberId(memberId);

        return chatList.stream()
                .map(GetChatRes::of)
                .collect(Collectors.toList());
    }

}
