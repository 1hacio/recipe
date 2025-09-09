package com.recipick.backend.service;

import com.recipick.backend.dto.RecipeDto;
import com.recipick.backend.util.RecipeCsvLoader;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RecipeService {

    private final List<RecipeDto> allRecipes;

    public RecipeService() {
        // 실제 운영 환경에서는 DB에서 데이터를 로드해야 함
        this.allRecipes = RecipeCsvLoader.loadRecipesFromCsv("recipe.csv");
    }

    public List<RecipeDto> recommendRecipes(List<String> ingredients) {
        // 재료 목록을 소문자 & 공백 제거 후 사용
        Set<String> ingredientSet = new HashSet<>();
        for (String ingredient : ingredients) {
            ingredientSet.add(normalize(ingredient));
        }

        List<RecipeDto> scoredRecipes = new ArrayList<>();
        for (RecipeDto recipe : this.allRecipes) {
            List<String> have = new ArrayList<>();
            List<String> missing = new ArrayList<>();

            // 레시피 재료를 정규화하여 매칭
            List<String> recipeIngredients = normalizeIngredients(recipe.getHave());
            for (String recipeIngredient : recipeIngredients) {
                if (ingredientSet.contains(recipeIngredient)) {
                    have.add(recipeIngredient);
                } else {
                    missing.add(recipeIngredient);
                }
            }

            // 보유 재료와 부족 재료를 계산하여 DTO에 설정
            RecipeDto newRecipe = new RecipeDto();
            newRecipe.setSeq(recipe.getSeq());
            newRecipe.setName(recipe.getName());
            newRecipe.setLink(recipe.getLink());
            newRecipe.setImage(recipe.getImage());
            newRecipe.setHave(have);
            newRecipe.setMissing(missing);

            scoredRecipes.add(newRecipe);
        }

        // 보유 재료가 많은 순, 부족 재료가 적은 순으로 정렬
        scoredRecipes.sort(Comparator.comparingInt((RecipeDto r) -> r.getHave().size()).reversed()
                .thenComparingInt(r -> r.getMissing().size()));

        // 정렬된 레시피에 색상 할당 (프론트엔드와 동일하게)
        String[] colors = {"#ffb6c1", "#d2b48c", "#add8e6", "#90ee90", "#dda0dd"};
        for (int i = 0; i < scoredRecipes.size(); i++) {
            scoredRecipes.get(i).setColor(colors[i % colors.length]);
        }

        return scoredRecipes;
    }

    private String normalize(String s) {
        return s.toLowerCase().replaceAll("[^a-z0-9가-힣]", "");
    }

    private List<String> normalizeIngredients(List<String> ingredients) {
        List<String> normalizedList = new ArrayList<>();
        for (String ingredient : ingredients) {
            normalizedList.add(normalize(ingredient));
        }
        return normalizedList;
    }
}