package com.remind.memorylog.global.external;

import com.remind.memorylog.global.config.OpenAiConfig;
import com.remind.memorylog.global.external.exception.OpenAiApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiService {
    private final OpenAiConfig openAiConfig;
    private final RestTemplate restTemplate;

    private static final String CHAT_URL = "https://api.openai.com/v1/chat/completions";

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