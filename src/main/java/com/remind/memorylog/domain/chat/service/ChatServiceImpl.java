package com.remind.memorylog.domain.chat.service;

import com.remind.memorylog.domain.chat.entity.Chat;
import com.remind.memorylog.domain.chat.entity.enums.Sender;
import com.remind.memorylog.domain.chat.repository.ChatRepository;
import com.remind.memorylog.domain.chat.web.dto.GetChatRes;
import com.remind.memorylog.domain.chat.web.dto.SendChatReq;
import com.remind.memorylog.domain.chat.web.dto.SendChatRes;
import com.remind.memorylog.domain.diary.service.S3Uploader;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import com.remind.memorylog.global.external.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final OpenAiService openAiService;
    private final S3Uploader s3Uploader;

    /**
     * 대화 전송
     */
    @Override
    public SendChatRes sendChat(SendChatReq sendChatReq, MultipartFile image) throws IOException {
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(sendChatReq.memberId());

        Chat userChat = Chat.toEntity(sendChatReq.content(), member, Sender.USER);
        chatRepository.save(userChat);

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Uploader.upload(image, "chat");
        }
        System.out.println("이미지" + imageUrl);

        String answer;
        if (imageUrl != null) {
            answer = openAiService.getAnswer(sendChatReq, imageUrl);
        } else {
            answer = openAiService.getAnswer(sendChatReq, null);
        }

        Chat rimiChat = Chat.toEntity(answer, member, Sender.RIMI);
        chatRepository.save(rimiChat);

        return SendChatRes.of(rimiChat);
    }

}
