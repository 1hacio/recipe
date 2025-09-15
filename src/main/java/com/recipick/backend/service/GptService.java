// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/service/GptService.java

package com.recipick.backend.service;

import com.recipick.backend.config.GptConfig;
import com.recipick.backend.dto.GptRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GptService {

    private final RestTemplate restTemplate;
    private final GptConfig gptConfig;

    public GptService(RestTemplate restTemplate, GptConfig gptConfig) {
        this.restTemplate = restTemplate;
        this.gptConfig = gptConfig;
    }

    public Object getAiRecipe(GptRequestDto requestDto) {
        String prompt = buildPrompt(requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(gptConfig.getSecretKey());

        String requestBody = "{\"model\": \"" + gptConfig.getModel() + "\", \"prompt\": \"" + prompt.replace("\"", "\\\"") + "\", \"max_tokens\": 1000}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // OpenAI API 엔드포인트는 모델에 따라 다를 수 있으므로 확인이 필요합니다.
        // 예시: "https://api.openai.com/v1/completions"
        return restTemplate.postForObject("https://api.openai.com/v1/completions", request, Object.class);
    }

    private String buildPrompt(GptRequestDto requestDto) {
        String ingredientsList = String.join(", ", requestDto.getIngredients());
        String userLine = "current".equals(requestDto.getMode())
                ? String.format("현재 가지고 있는 재료는 %s 입니다. 이 재료들을 활용해 새로운 레시피를 창작해주세요.", ingredientsList)
                : String.format("\"%s\" 컨셉의 레시피를 창작해주세요. 현재 가진 재료는 %s 입니다.", requestDto.getDesiredInput(), ingredientsList);

        String modifiers = buildModifiersString(requestDto.getModifiers());

        // 프론트엔드의 buildPrompt와 유사한 프롬프트 구조를 만듭니다.
        // (보안 및 안정성을 위해 실제 서비스에서는 더 정교한 프롬프트 엔지니어링이 필요합니다.)
        return "# 출력 규칙: JSON 형식으로 '이름', '재료', '레시피', '키워드' 키를 포함하여 반환하세요.\\n" +
                "# 사용자 요청: " + userLine + "\\n" +
                "# 선호/조건: " + modifiers;
    }

    // 프론트의 buildModifiers와 유사한 기능을 하는 헬퍼 메서드
    private String buildModifiersString(GptRequestDto.Modifiers modifiers) {
        if (modifiers == null) return "";
        StringBuilder sb = new StringBuilder();
        if (modifiers.getMeal() != null && !modifiers.getMeal().isEmpty()) sb.append("- 끼니: ").append(modifiers.getMeal()).append("\\n");
        if (modifiers.getSpicy() != null && !modifiers.getSpicy().isEmpty()) sb.append("- 매운맛: ").append(modifiers.getSpicy()).append("\\n");
        // ... 다른 모든 modifier들을 문자열로 변환하는 로직 추가 ...
        return sb.toString();
    }
}