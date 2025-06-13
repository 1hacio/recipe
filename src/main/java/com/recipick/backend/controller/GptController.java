package com.recipick.backend.controller;

import com.recipick.backend.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpt")
public class GptController {

    private final GptService gptService;

    @GetMapping("/recommend")
    public String recommend(@RequestParam String prompt) {
        return gptService.askGpt(prompt);
    }
}
