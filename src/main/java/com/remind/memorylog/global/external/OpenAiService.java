package com.remind.memorylog.global.external;

import com.remind.memorylog.domain.chat.entity.Chat;
import com.remind.memorylog.domain.chat.entity.enums.Sender;
import com.remind.memorylog.domain.chat.repository.ChatRepository;
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
import org.springframework.web.multipart.MultipartFile;

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

    private static final String MISSION_PROMPT = "당신은 연인과의 미션을 생성하는 전문가입니다. " +
            "사용자와의 대화 내역을 참고하여 사용자에게 맞는 미션을 제안해주세요. " +
            "응답은 반드시 다음 JSON 형식으로 제공해주세요:\n\n" +
            "{\n" +
            "  \"title\": \"미션의 제목\",\n" +
            "  \"description\": \"미션 간단 설명\",\n" +
            "  \"mission1\": \"첫 번째 상세 미션\",\n" +
            "  \"mission2\": \"두 번째 상세 미션\",\n" +
            "  \"mission3\": \"세 번째 상세 미션\"\n" +
            "}\n\n" +
            "절대 설명식 문장 없이 JSON만 응답해주세요." +
            "다음은 사용자와 당신의 대화 내역입니다 : ";

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

    private static final String CHAT_PROMPT="당신은 ‘리미’라는 이름을 가진 연애 감정 기록 전문가입니다.  \n" +
            "사용자의 말투, 감정, 상황을 잘 파악해서 자연스럽고 친근하게 정보를 수집해야 합니다.  \n" +
            "친구처럼 부드럽고 공감하는 반말로 대화해주세요.  \n" +
            "단, 물어봤던 내용을 또 다시 질문하지 마세요. \n" +
            "만약 사진이 있다면, 사진을 참고하여 대화하세요. \n" +
            "사용자가 연애 관련 고민이나 상황을 이야기하면, 아래 항목을 순서대로 물어보면서 대화를 이어가세요:  \n" +
            "1. 안녕! 나는 너희 둘의 기억을 함께 모아줄 감정 기록 AI, 리미야! 오늘 우리만의 특별한 대화를 시작해볼까? 먼저 너의 이름을 알려줘!  \n" +
            "2. 고마워! 그럼 이번엔 너의 연인 이름도 알려줘. 둘만의 이야기를 더 따뜻하게 기록하고 싶거든.  \n" +
            "3. 너는 어디 근처에 살아? 그 공간도 너의 이야기 중 하나니까, 리미가 알고 싶어!  \n" +
            "4. 애인은 어디 살아? 너희 동선을 보면 리미가 어울리는 미션을 떠올릴 수 있을 것 같아!  \n" +
            "\n" +
            "이 4가지 질문을 모두 물어봤으면, 주제에 맞는 새로운 질문이나 감정을 더 깊게 탐색하는 질문을 하면 됩니다.  \n" +
            "\n" +
            "다음은 첫 질문 예시입니다:  \n" +
            "“그 사람과 가장 기억에 남는 장소가 어디야?”  \n" +
            "\n" +
            "아래는 사용자와 너의 대화 내역입니다.  \n" +
            "※ 참고: 헤당 대화내역에는 사용자의 이름을 묻는 부분이 포함되어 있지 않습니다.  \n" +
            "하지만 AI는 대화의 가장 첫 부분에서 이미 사용자의 이름을 물어봤다고 간주하고,  \n" +
            "그 이후의 대화를 자연스럽게 이어가 주세요. 오직 답변만 보내주면 됩니다. '리미:' 이런 표시를 넣지 마세요." +

            "사용자의 마지막 대화에 답해주세요:  \n";

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

    /**
     * 미션 생성
     */
    public String createMission(Long memberId){
        List<GetChatRes> chatResList = getChatList(memberId);

        StringBuilder chatHistory = new StringBuilder();

        for (GetChatRes chat : chatResList) {
            if (chat.sender() == Sender.USER) {
                chatHistory.append("User: ").append(chat.content()).append("\n");
            } else if (chat.sender() == Sender.RIMI) {
                chatHistory.append("리미: ").append(chat.content()).append("\n");
            }
        }

        String prompt = MISSION_PROMPT + chatHistory;
        Map<String, Object> requestBody = createRequestBody(prompt);
        ResponseEntity<Map> response = sendRequest(requestBody);
        return parseResponse(response);
    }

    /**
     * 데이트코스 생성
     */
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
     * 사용자의 질문에 답변
     */
    public String getAnswer(SendChatReq sendChatReq, String imgUrl){
        List<GetChatRes> chatResList = getChatList(sendChatReq.memberId());

        StringBuilder chatHistory = new StringBuilder();

        for (GetChatRes chat : chatResList) {
            if (chat.sender() == Sender.USER) {
                chatHistory.append("User: ").append(chat.content()).append("\n");
            } else if (chat.sender() == Sender.RIMI) {
                chatHistory.append("리미: ").append(chat.content()).append("\n");
            }
        }

        String prompt = CHAT_PROMPT + chatHistory.toString();
        System.out.println("프롬프트" + prompt);

        Map<String, Object> requestBody;
        if (imgUrl == null || imgUrl.isEmpty()) {
            requestBody = createRequestBody(prompt);
        } else {
            requestBody = createRequestBodyWithImg(imgUrl, prompt);
        }

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