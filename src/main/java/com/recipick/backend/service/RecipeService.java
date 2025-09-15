// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/service/RecipeService.java

package com.recipick.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; // JSON 파싱을 위해 추가

    @Value("${api.food-safety-korea.key}")
    private String foodSafetyApiKey;

    public RecipeService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // 두 API의 추천 결과를 모두 반환하는 메인 메서드
    public Map<String, Object> getAllRecommendations(List<String> ingredients) {
        Map<String, Object> recommendations = new HashMap<>();

        // 1. 식약처 API 결과
        Object foodSafetyResult = getFoodSafetyRecommendations(ingredients);
        recommendations.put("foodSafety", foodSafetyResult);

        // 2. MealDB API 결과
        Object mealDbResult = getMealDbRecommendations(ingredients);
        recommendations.put("mealDb", mealDbResult);

        return recommendations;
    }


    // 식약처 API 호출
    private Object getFoodSafetyRecommendations(List<String> ingredients) {
        if (foodSafetyApiKey == null || foodSafetyApiKey.equals("YOUR_API_KEY") || foodSafetyApiKey.isEmpty()) {
            return Map.of("error", "식약처 API 키가 설정되지 않았습니다.");
        }

        String ingredientString = ingredients.stream()
                .map(ingredient -> URLEncoder.encode(ingredient, StandardCharsets.UTF_8))
                .collect(Collectors.joining("|"));

        String url = String.format(
                "http://openapi.foodsafetykorea.go.kr/api/%s/COOKRCP01/json/1/100/RCP_PARTS_DTLS=%s",
                foodSafetyApiKey, ingredientString
        );

        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(jsonResponse, Object.class); // JSON 문자열을 객체로 변환
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Failed to fetch recipes from Food Safety Korea API");
        }
    }

    // MealDB API 호출 (새로 추가)
    private Object getMealDbRecommendations(List<String> ingredients) {
        // MealDB는 주로 재료 1개로 검색하므로, 첫 번째 재료를 사용합니다.
        if (ingredients == null || ingredients.isEmpty()) {
            return Map.of("error", "No ingredients provided for MealDB search");
        }
        String firstIngredient = URLEncoder.encode(ingredients.get(0), StandardCharsets.UTF_8);
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + firstIngredient;

        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(jsonResponse, Object.class); // JSON 문자열을 객체로 변환
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Failed to fetch recipes from TheMealDB API");
        }
    }
}