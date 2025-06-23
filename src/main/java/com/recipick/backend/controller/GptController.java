package com.recipick.backend.controller;

import com.recipick.backend.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpt")
public class GptController {

    private final GptService gptService;

    // ✅ GET 방식 (간단 테스트용)
    @GetMapping("/recommend")
    public String recommend(@RequestParam String prompt) {
        return gptService.askGpt(prompt);
    }

    // ✅ POST 방식 (실제 프론트 연동용)
    @PostMapping("/recommend")
    public String recommendPost(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        return gptService.askGpt(prompt);
    }
}
