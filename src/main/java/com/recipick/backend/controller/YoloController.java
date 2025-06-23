package com.recipick.backend.controller;

import com.recipick.backend.dto.RecipeDto;
import com.recipick.backend.service.RecipeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yolo")
public class YoloController {

    private final RecipeService recipeService;

    @PostMapping("/ingredients")
    public ResponseEntity<List<RecipeDto>> receiveYoloIngredients(@RequestBody YoloRequest request) {
        List<String> ingredients = request.getIngredients();

        // 서비스 호출로 레시피 추천 결과 얻기
        List<RecipeDto> recommended = recipeService.recommendRecipes(ingredients);

        return ResponseEntity.ok(recommended);
    }

    @Data
    static class YoloRequest {
        private List<String> ingredients;
    }
}
