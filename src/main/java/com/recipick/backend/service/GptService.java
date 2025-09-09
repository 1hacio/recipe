package com.recipick.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipick.backend.dto.AiRecipeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptService {

    @Value("${openai.secret-key:dummy}")
    private String openaiApiKey;

    @Value("${openai.model:gpt-3.5-turbo}")
    private String model;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String askGpt(String prompt) {
        try {
            String url = "https://api.openai.com/v1/chat/completions";

            Map<String, Object> message = Map.of(
                    "role", "user",
                    "content", prompt
            );

            Map<String, Object> body = Map.of(
                    "model", model,
                    "messages", List.of(message),
                    "temperature", 0.7
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openaiApiKey);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

            // GPT 응답 파싱
            if (response.getBody() != null && response.getBody().containsKey("choices")) {
                List<Map> choices = (List<Map>) response.getBody().get("choices");
                if (!choices.isEmpty()) {
                    Map choice = choices.get(0);
                    if (choice.containsKey("message")) {
                        Map messageResp = (Map) choice.get("message");
                        if (messageResp.containsKey("content")) {
                            return messageResp.get("content").toString().trim();
                        }
                    }
                }
            }

            // 응답 구조가 예상과 다를 경우
            log.warn("GPT 응답 구조가 예상과 다릅니다: {}", response.getBody());
            return getFallbackResponse(prompt);

        } catch (RestClientException e) {
            log.error("GPT API 호출 중 오류 발생: {}", e.getMessage(), e);
            return getFallbackResponse(prompt);
        } catch (Exception e) {
            log.error("GPT 서비스 처리 중 예상치 못한 오류: {}", e.getMessage(), e);
            return getFallbackResponse(prompt);
        }
    }

    public AiRecipeDto getAiRecipe(String prompt) {
        String jsonResponse = askGpt(prompt);

        try {
            return objectMapper.readValue(jsonResponse, AiRecipeDto.class);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage(), e);
            return null;
        }
    }

    private String getFallbackResponse(String prompt) {
        if (prompt.toLowerCase().contains("레시피") || prompt.toLowerCase().contains("요리")) {
            return "죄송합니다. 현재 AI 서비스에 일시적인 문제가 있습니다. 대신 재료를 입력하시면 관련 레시피를 추천해드리겠습니다.";
        }
        return "죄송합니다. 현재 AI 서비스에 일시적인 문제가 있습니다. 잠시 후 다시 시도해주세요.";
    }
}