// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/service/RecipeService.java

package com.recipick.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipick.backend.model.Recipe;
import com.recipick.backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final RecipeRepository recipeRepository;

    @Value("${api.food-safety-korea.key}")
    private String foodSafetyApiKey;

    public RecipeService(RestTemplate restTemplate, ObjectMapper objectMapper, RecipeRepository recipeRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.recipeRepository = recipeRepository;
    }

    public Set<Recipe> searchRecipesByIngredients(List<String> ingredients) {
        Set<Recipe> matchedRecipes = new HashSet<>();
        for (String ingredient : ingredients) {
            matchedRecipes.addAll(recipeRepository.findByIngredientsContaining(ingredient));
        }
        return matchedRecipes;
    }

    public Map<String, Object> getAllRecommendations(List<String> ingredients) {
        Map<String, Object> recommendations = new HashMap<>();
        recommendations.put("foodSafety", getFoodSafetyRecommendations(ingredients));
        recommendations.put("mealDb", getMealDbRecommendations(ingredients));
        return recommendations;
    }

    private Object getFoodSafetyRecommendations(List<String> ingredients) {
        if (foodSafetyApiKey == null || foodSafetyApiKey.equals("YOUR_API_KEY") || foodSafetyApiKey.isEmpty()) {
            return Map.of("error", "식약처 API 키가 설정되지 않았습니다.");
        }
        String ingredientString = ingredients.stream()
                .map(ingredient -> URLEncoder.encode(ingredient, StandardCharsets.UTF_8))
                .collect(Collectors.joining("|"));
        String url = String.format(
                "http://openapi.foodsafetykorea.go.kr/api/%s/COOKRCP01/json/1/100/RCP_PARTS_DTLS=%s",
                foodSafetyApiKey, ingredientString);
        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(jsonResponse, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Failed to fetch recipes from Food Safety Korea API");
        }
    }

    // --- *** 이 메서드가 최종 수정되었습니다 *** ---
    private Object getMealDbRecommendations(List<String> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            return Map.of("meals", Collections.emptyList());
        }

        Set<String> mealIds = new HashSet<>();
        List<Map<String, String>> finalMealList = new ArrayList<>();

        for (String ingredient : ingredients) {
            try {
                String encodedIngredient = URLEncoder.encode(ingredient, StandardCharsets.UTF_8);
                String url = "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + encodedIngredient;

                // 1. API 응답을 가장 안전한 String 형태로 먼저 받습니다.
                String jsonResponse = restTemplate.getForObject(url, String.class);

                // 2. 응답이 null이 아니고 내용이 있을 때만 파싱을 시도합니다.
                if (jsonResponse != null && !jsonResponse.trim().isEmpty() && !jsonResponse.trim().equals("{\"meals\":null}")) {
                    Map<String, List<Map<String, String>>> response = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

                    if (response != null && response.get("meals") != null) {
                        for (Map<String, String> meal : response.get("meals")) {
                            if (meal != null && meal.get("idMeal") != null && mealIds.add(meal.get("idMeal"))) {
                                finalMealList.add(meal);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // 에러가 발생해도 다른 재료 검색은 계속 진행합니다.
                System.err.println("MealDB API 호출 실패 (재료: " + ingredient + "): " + e.getMessage());
            }
        }
        // 프론트엔드가 기대하는 최종 응답 형식인 { "meals": [...] } Map으로 감싸서 반환합니다.
        return Map.of("meals", finalMealList);
    }
}