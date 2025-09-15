// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/controller/GptController.java

package com.recipick.backend.controller;

import com.recipick.backend.dto.GptRequestDto;
import com.recipick.backend.service.GptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpt")
public class GptController {

    private final GptService gptService;

    public GptController(GptService gptService) {
        this.gptService = gptService;
    }

    @PostMapping("/recommendations")
    public ResponseEntity<Object> getGptRecipe(@RequestBody GptRequestDto requestDto) {
        Object result = gptService.getAiRecipe(requestDto);
        return ResponseEntity.ok(result);
    }
}