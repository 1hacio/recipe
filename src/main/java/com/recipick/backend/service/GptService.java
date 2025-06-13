package com.recipick.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GptService {

    @Value("${openai.secret-key:dummy}")
    private String openaiApiKey;

    @Value("${openai.model:gpt-3.5-turbo}")
    private String model;

    public String askGpt(String prompt) {
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
        Map choice = ((List<Map>) response.getBody().get("choices")).get(0);
        Map messageResp = (Map) choice.get("message");

        return messageResp.get("content").toString().trim();
    }
}
