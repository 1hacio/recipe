package com.recipick.backend.service;

import com.recipick.backend.dto.RecipeDto;
import com.recipick.backend.model.Inventory;
import com.recipick.backend.model.Recipe;
import com.recipick.backend.model.RecipeIngredient;
import com.recipick.backend.model.User;
import com.recipick.backend.repository.InventoryRepository;
import com.recipick.backend.repository.RecipeIngredientRepository;
import com.recipick.backend.repository.RecipeRepository;
import com.recipick.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository ingredientRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final GptService gptService;

    // ✅ 1. ChatGPT 기반 추천
    public String recommendWithGpt(String prompt) {
        return gptService.askGpt(prompt);
    }

    // ✅ 2. 재료 목록 기반 추천
    public List<RecipeDto> recommendRecipes(List<String> ingredients) {
        return recommendRecipes(ingredients, null, null, null);
    }

    // ✅ 3. 필터 포함된 재료 기반 추천
    public List<RecipeDto> recommendRecipes(List<String> ingredients, String category, String difficulty, Integer maxTime) {
        List<RecipeIngredient> matchedIngredients = ingredientRepository.findByIngredient_NameIn(ingredients);

        // 레시피별 매칭된 재료 수 계산
        Map<Recipe, Long> recipeCountMap = matchedIngredients.stream()
                .collect(Collectors.groupingBy(RecipeIngredient::getRecipe, Collectors.counting()));

        return recipeCountMap.entrySet().stream()
                .filter(entry -> {
                    Recipe recipe = entry.getKey();

                    // 필터: 카테고리
                    if (category != null && !category.isEmpty()) {
                        if (!category.equals(recipe.getMainIngredient())
                                && !category.equals(recipe.getDishType())
                                && !category.equals(recipe.getCookingMethod())) {
                            return false;
                        }
                    }

                    // 필터: 난이도
                    if (difficulty != null && !difficulty.isEmpty()) {
                        if (!difficulty.equals(recipe.getDifficulty())) {
                            return false;
                        }
                    }

                    // 필터: 조리 시간
                    if (maxTime != null) {
                        String cookingTime = recipe.getCookingTime();
                        if (cookingTime != null) {
                            try {
                                String timeStr = cookingTime.replaceAll("[^0-9]", "");
                                if (!timeStr.isEmpty()) {
                                    int time = Integer.parseInt(timeStr);
                                    if (time > maxTime) return false;
                                }
                            } catch (NumberFormatException e) {
                                // 무시하고 포함
                            }
                        }
                    }

                    return true;
                })
                .sorted((e1, e2) -> {
                    // 정렬 우선순위: ①재료수 ②조회수 ③추천수
                    int compareMatched = e2.getValue().compareTo(e1.getValue());
                    if (compareMatched != 0) return compareMatched;

                    int views1 = Optional.ofNullable(e1.getKey().getViewCount()).orElse(0);
                    int views2 = Optional.ofNullable(e2.getKey().getViewCount()).orElse(0);
                    if (views1 != views2) return Integer.compare(views2, views1);

                    int recs1 = Optional.ofNullable(e1.getKey().getRecommendCount()).orElse(0);
                    int recs2 = Optional.ofNullable(e2.getKey().getRecommendCount()).orElse(0);
                    return Integer.compare(recs2, recs1);
                })
                .limit(20)
                .map(entry -> {
                    Recipe recipe = entry.getKey();
                    List<String> ingredientNames = recipe.getRecipeIngredients().stream()
                            .map(i -> i.getIngredient().getName())
                            .collect(Collectors.toList());

                    return new RecipeDto(
                            recipe.getId(),
                            recipe.getTitle(),
                            recipe.getDescription(),
                            recipe.getCookingTime(),
                            recipe.getDifficulty(),
                            recipe.getImageUrl(),
                            ingredientNames
                    );
                })
                .collect(Collectors.toList());
    }

    // ✅ 4. 사용자 재고 기반 추천
    public List<RecipeDto> recommendByInventory(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userEmail));

        List<String> ingredients = inventoryRepository.findByUser(user).stream()
                .map(Inventory::getName)
                .collect(Collectors.toList());

        return recommendRecipes(ingredients);
    }
}
