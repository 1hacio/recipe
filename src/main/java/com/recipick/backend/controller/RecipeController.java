package com.recipick.backend.controller;

import java.util.List;
import com.recipick.backend.dto.RecipeDto;
import com.recipick.backend.service.RecipeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recommend")
    public List<RecipeDto> recommend(@RequestBody List<String> ingredients) {
        return recipeService.recommendRecipes(ingredients);
    }
    @PostMapping("/gpt-recommend")
    public String gptRecommend(@RequestBody List<String> ingredients) {
        String prompt = String.format("다음 재료로 만들 수 있는 요리를 하나 추천해줘: %s. 요리 이름과 간단한 설명만 알려줘.", String.join(", ", ingredients));
        return recipeService.recommendWithGpt(prompt);
    }

}
