// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/controller/GeminiController.java

package com.recipick.backend.controller;

import com.recipick.backend.dto.GeminiRequestDto;
import com.recipick.backend.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/recommendations")
    public ResponseEntity<Object> getGeminiRecipe(@RequestBody GeminiRequestDto requestDto) {
        try {
            Object result = geminiService.getAiRecipe(requestDto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("An error occurred while processing the AI recipe request.");
        }
    }
}