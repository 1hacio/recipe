package com.recipick.backend.controller;

import com.recipick.backend.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpt")
public class GptController {

    private final GptService gptService;

    // ✅ 1. GET 방식 (간단 테스트용 - 주소창에서 직접 요청 가능)
    @GetMapping("/recommend")
    public String recommend(@RequestParam String prompt) {
        return gptService.askGpt(prompt);
    }

    // ✅ 2. POST 방식 (프론트에서 prompt 전송 시 사용)
    @PostMapping("/recommend")
    public String recommendPost(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        return gptService.askGpt(prompt);
    }

    // ✅ 3. 재료 기반 POST 요청 (예: ["양파", "계란"])
    @PostMapping("/recommend-by-ingredients")
    public String recommendByIngredients(@RequestBody List<String> ingredients) {
        String prompt = String.format(
                "다음 재료로 만들 수 있는 요리를 추천해줘. 요리 이름과 간단한 만드는 방법까지 알려줘: %s",
                String.join(", ", ingredients)
        );
        return gptService.askGpt(prompt);
    }
}
