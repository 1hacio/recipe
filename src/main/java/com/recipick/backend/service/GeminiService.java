// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/service/GeminiService.java

package com.recipick.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipick.backend.config.GeminiConfig;
import com.recipick.backend.dto.GeminiRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final RestTemplate restTemplate;
    private final GeminiConfig geminiConfig;
    private final ObjectMapper objectMapper;

    public GeminiService(RestTemplate restTemplate, GeminiConfig geminiConfig, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.geminiConfig = geminiConfig;
        this.objectMapper = objectMapper;
    }

    public Object getAiRecipe(GeminiRequestDto requestDto) throws Exception {
        String apiKey = geminiConfig.getApiKey();
        if (apiKey == null || apiKey.startsWith("YOUR_") || apiKey.isEmpty()) {
            return Map.of("error", "Gemini API 키가 설정되지 않았습니다.");
        }

        String url = "https://generativelanguage.googleapis.com/v1beta/models/" + geminiConfig.getModel() + ":generateContent?key=" + apiKey;

        // buildPrompt 메서드를 호출하도록 수정
        String prompt = buildPrompt(requestDto);

        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", prompt);
        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(textPart));
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("contents", List.of(content));

        Map<String, String> mimeType = new HashMap<>();
        mimeType.put("responseMimeType", "application/json");
        requestBodyMap.put("generationConfig", mimeType);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(requestBodyMap), headers);
        return restTemplate.postForObject(url, request, Object.class);
    }

    // 프롬프트 생성 로직 수정
    private String buildPrompt(GeminiRequestDto requestDto) {
        String ingredientsList = String.join(", ", requestDto.getIngredients());
        String userLine = "current".equals(requestDto.getMode())
                ? String.format("현재 가지고 있는 재료는 %s 입니다. 이 재료들을 활용해 새로운 레시피를 창작해주세요.", ingredientsList)
                : String.format("\"%s\" 컨셉의 레시피를 창작해주세요. 현재 가진 재료는 %s 입니다.", requestDto.getDesiredInput(), ingredientsList);

        // 프론트에서 받은 modifiers 문자열을 그대로 사용합니다.
        String modifiers = requestDto.getModifiers();

        // 프론트엔드의 ai.ts와 동일한 프롬프트 구조
        return String.format(
                "# 출력 규칙 (매우 중요)\\n - 오직 JSON 하나만 반환하세요. ... (생략) ... \\n# 사용자 요청\\n %s\\n\\n# 선호/조건\\n%s",
                userLine, modifiers
        );
    }
}