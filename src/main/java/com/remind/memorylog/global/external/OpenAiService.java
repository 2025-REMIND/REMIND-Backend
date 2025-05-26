package com.remind.memorylog.global.external;

import com.remind.memorylog.domain.chat.entity.Chat;
import com.remind.memorylog.domain.chat.entity.enums.Sender;
import com.remind.memorylog.domain.chat.repository.ChatRepository;
import com.remind.memorylog.domain.chat.service.ChatService;
import com.remind.memorylog.domain.chat.web.dto.GetChatRes;
import com.remind.memorylog.domain.chat.web.dto.SendChatReq;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import com.remind.memorylog.global.config.OpenAiConfig;
import com.remind.memorylog.global.external.exception.OpenAiApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenAiService {
    private final OpenAiConfig openAiConfig;
    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;

    private static final String CHAT_URL = "https://api.openai.com/v1/chat/completions";

    private static final String COURSE_PROMPT = "당신은 데이트 코스를 기획하는 전문가입니다. " +
            "사용자와의 대화 내역을 참고하여 상황에 맞는 데이트 코스를 제안해주세요. " +
            "응답은 반드시 다음 JSON 형식으로 제공해주세요:\n\n" +
            "{\n" +
            "  \"title\": \"코스의 제목\",\n" +
            "  \"place\": \"장소명\",\n" +
            "  \"description\": \"코스 간단 설명\",\n" +
            "  \"time\": \"추천 시간대\",\n" +
            "  \"mission1\": \"첫 번째 활동\",\n" +
            "  \"mission2\": \"두 번째 활동\",\n" +
            "  \"mission3\": \"세 번째 활동\"\n" +
            "}\n\n" +
            "절대 설명식 문장 없이 JSON만 응답해주세요." +
            "다음은 사용자와 당신의 대화 내역입니다 : ";

    private static final String CHAT_PROMPT="당신은 리미라는 이름을 가진 연애 전문가입니다. " +
            "사용자의 말투, 감정, 상황을 파악해 자연스럽게 정보를 수집하세요." +
            "사용자가 연애 상황이나 고민을 이야기하면 다음과 같은 항목들을 순차적으로 물어보며 대화를 이어가세요." +
            "아래 항목을 모두 물어봤을 경우에는 새로운 질문을 하면 됩니다. :\n" +
            "1. 안녕! 나는 너희 둘의 기억을 함께 모아줄 감정 기록 AI, 리미야! 오늘, 우리만의 특별한 대화를 시작해볼까? 먼저 너의 이름을 알려줘!" +
            "2. 고마워! 그럼 이번엔 너의 연인 이름도 알려줘. 둘만의 이야기를 더 따뜻하게 기록하고 싶거든." +
            "3. 너는 어디 근처에 살고 있어? 그 공간도 너의 이야기 중 하나니까, 리미가 알고 싶어!" +
            "4. 애인은 어디 살고 있어? 너희 동선 보면 리미가 어울리는 미션을 떠올릴 수 있을 것 같아!" +
            "친구처럼 반말로 공감적인 말투로 대화를 이어가고, 사용자의 감정을 존중하며 정보를 수집해야 합니다.\n" +
            "다음은 첫 질문 예시입니다: " + "\"그 사람과 가장 기억에 남는 장소가 어디야?\"\n" +
            "사용자와 당신의 대화 내역입니다. 사용자의 마지막 질문에 답해주세요 : ";

    /**
     * 사용자와의 대화 내역 조회 (ai에게 대화 내역을 전송하기 위함)
     */
    public List<GetChatRes> getChatList(Long memberId){
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        List<Chat> chatList = chatRepository.findAllByMember_MemberId(memberId);

        return chatList.stream()
                .map(GetChatRes::of)
                .collect(Collectors.toList());
    }

    public String createCourse(Long memberId){
        List<GetChatRes> chatResList = getChatList(memberId);

        StringBuilder chatHistory = new StringBuilder();

        for (GetChatRes chat : chatResList) {
            if (chat.sender() == Sender.USER) {
                chatHistory.append("User: ").append(chat.content()).append("\n");
            } else if (chat.sender() == Sender.RIMI) {
                chatHistory.append("리미: ").append(chat.content()).append("\n");
            }
        }

        String prompt = COURSE_PROMPT + chatHistory;
        Map<String, Object> requestBody = createRequestBody(prompt);
        ResponseEntity<Map> response = sendRequest(requestBody);
        return parseResponse(response);
    }

    /**
     * 사용자의 질문의 답변
     */
    public String getAnswer(SendChatReq sendChatReq){
        List<GetChatRes> chatResList = getChatList(sendChatReq.memberId());

        StringBuilder chatHistory = new StringBuilder();

        for (GetChatRes chat : chatResList) {
            if (chat.sender() == Sender.USER) {
                chatHistory.append("User: ").append(chat.content()).append("\n");
            } else if (chat.sender() == Sender.RIMI) {
                chatHistory.append("리미: ").append(chat.content()).append("\n");
            }
        }
        chatHistory.append("User: ").append(sendChatReq.content());

        String prompt = CHAT_PROMPT + sendChatReq.content();
        Map<String, Object> requestBody = createRequestBody(prompt);
        ResponseEntity<Map> response = sendRequest(requestBody);
        return parseResponse(response);
    }

    public String getDateCourse(String prompt) {
        Map<String, Object> requestBody = createRequestBody(prompt);
        ResponseEntity<Map> response = sendRequest(requestBody);
        return parseResponse(response);
    }

    /**
     * 텍스트로만 RequestBody 생성
     */
    public Map<String, Object> createRequestBody(String prompt) {
        return Map.of(
                "model", openAiConfig.getModel(),
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );
    }

    /**
     * 이미지와 함께 RequestBody 생성
     */
    public Map<String, Object> createRequestBodyWithImg(String imageUrl, String prompt){
        return Map.of(
                "model", openAiConfig.getModel(),
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", List.of(
                                        Map.of(
                                                "type", "image_url",
                                                "image_url", Map.of("url", imageUrl)
                                        ),
                                        Map.of(
                                                "type", "text",
                                                "text", prompt
                                        )
                                )
                        )
                )
        );
    }

    /**
     * 요청 전송
     */
    public ResponseEntity<Map> sendRequest(Map<String, Object> requestBody){
        HttpHeaders headers = openAiConfig.createOpenAiHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            return restTemplate.exchange(CHAT_URL, HttpMethod.POST, request, Map.class);
        } catch (Exception e) {
            throw new OpenAiApiException();
        }
    }

    /**
     * 응답 파싱
     */
    public String parseResponse(ResponseEntity<Map> response){
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        return (String) message.get("content");
    }
}