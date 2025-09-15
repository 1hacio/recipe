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
import java.util.stream.Collectors;

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
        String prompt = buildPrompt(requestDto);

        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", prompt);
        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(textPart));
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("contents", List.of(content));

        // Gemini가 JSON을 반환하도록 설정 추가
        Map<String, String> mimeType = new HashMap<>();
        mimeType.put("responseMimeType", "application/json");
        requestBodyMap.put("generationConfig", mimeType);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(requestBodyMap), headers);
        return restTemplate.postForObject(url, request, Object.class);
    }

    private String buildPrompt(GeminiRequestDto requestDto) {
        String ingredientsList = String.join(", ", requestDto.getIngredients());
        String userLine = "current".equals(requestDto.getMode())
                ? String.format("현재 가지고 있는 재료는 %s 입니다. 이 재료들을 활용해 새로운 레시피를 창작해주세요.", ingredientsList)
                : String.format("\"%s\" 컨셉의 레시피를 창작해주세요. 현재 가진 재료는 %s 입니다.", requestDto.getDesiredInput(), ingredientsList);

        String modifiers = buildModifiersString(requestDto.getModifiers());

        // 프론트엔드의 ai.ts와 동일한 프롬프트 구조
        return String.format(
                "# 출력 규칙 (매우 중요)\\n - 오직 JSON 하나만 반환하세요. 마크다운, 코드펜스, 설명, 주석 금지.\\n - JSON의 최상위 키는 정확히 다음 4개만 허용됩니다: \"이름\", \"재료\", \"레시피\", \"키워드\".\\n - 각 필드의 형식:\\n   - \"이름\": string\\n   - \"재료\": object\\n       - \"보유재료\": string[]\\n       - \"추가추천재료\": string[]\\n   - \"레시피\": string[]\\n   - \"키워드\": string[]\\n - 위 형식을 위반하면 응답은 무효입니다.\\n\\n# 사용자 요청\\n %s%s",
                userLine, modifiers
        );
    }

    private String buildModifiersString(GeminiRequestDto.Modifiers modifiers) {
        if (modifiers == null) return "";
        StringBuilder sb = new StringBuilder();
        if (modifiers.getMeal() != null && !modifiers.getMeal().isEmpty()) sb.append(String.format("\\n- 끼니: %s", modifiers.getMeal()));
        if (modifiers.getTime() != null && !modifiers.getTime().isEmpty()) sb.append(String.format("\\n- 최대 조리 시간: %s분 이내", modifiers.getTime()));
        // ... 프론트엔드 AiRecipePanel.svelte의 buildModifiers() 함수와 동일하게 모든 옵션을 문자열로 만들어줍니다. ...
        return sb.toString();
    }
}