package com.remind.memorylog.domain.chat.service;

import com.remind.memorylog.domain.chat.entity.Chat;
import com.remind.memorylog.domain.chat.entity.enums.Sender;
import com.remind.memorylog.domain.chat.repository.ChatRepository;
import com.remind.memorylog.domain.chat.web.dto.GetChatRes;
import com.remind.memorylog.domain.chat.web.dto.SendChatReq;
import com.remind.memorylog.domain.chat.web.dto.SendChatRes;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import com.remind.memorylog.global.external.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final OpenAiService openAiService;
    /**
     * 대화 전송
     */
    @Override
    public SendChatRes sendChat(SendChatReq sendChatReq){
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(sendChatReq.memberId());

        Chat userChat = Chat.toEntity(sendChatReq.content(), member, Sender.USER);

        String answer = openAiService.getAnswer(sendChatReq);

        chatRepository.save(userChat);

        Chat rimiChat = Chat.toEntity(answer, member, Sender.RIMI);
        chatRepository.save(rimiChat);

        return SendChatRes.of(rimiChat);
    }

}
