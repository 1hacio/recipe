// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/controller/RecipeController.java

package com.recipick.backend.controller;

import com.recipick.backend.model.Recipe;
import com.recipick.backend.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * 외부 API(식약처, MealDB)를 통해 레시피를 추천받는 엔드포인트입니다.
     */
    @GetMapping("/recommendations")
    public ResponseEntity<Map<String, Object>> getRecommendations(@RequestParam List<String> ingredients) {
        // 디버깅용 로그: 프론트엔드에서 받은 재료 목록을 콘솔에 출력합니다.
        System.out.println("--- 프론트엔드에서 받은 재료 목록 (외부 API 추천) ---");
        System.out.println(ingredients);
        System.out.println("-------------------------------------------------");

        Map<String, Object> recommendations = recipeService.getAllRecommendations(ingredients);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 자체 데이터베이스(recipe.csv 기반)에서 재료를 검색하는 엔드포인트입니다.
     */
    @GetMapping("/search")
    public ResponseEntity<Set<Recipe>> searchRecipes(@RequestParam List<String> ingredients) {
        // 디버깅용 로그: 프론트엔드에서 받은 재료 목록을 콘솔에 출력합니다.
        System.out.println("--- 프론트엔드에서 받은 재료 목록 (DB 검색) ---");
        System.out.println(ingredients);
        System.out.println("-------------------------------------------");

        Set<Recipe> recipes = recipeService.searchRecipesByIngredients(ingredients);
        return ResponseEntity.ok(recipes);
    }
}