package com.recipick.backend.service;

import lombok.RequiredArgsConstructor;
import com.recipick.backend.dto.RecipeDto;
import com.recipick.backend.model.Recipe;
import com.recipick.backend.model.RecipeIngredient;
import com.recipick.backend.repository.RecipeIngredientRepository;
import com.recipick.backend.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository ingredientRepository;
    private final GptService gptService;

    public String recommendWithGpt(String prompt) {
        return gptService.askGpt(prompt);
    }

    public List<RecipeDto> recommendRecipes(List<String> ingredients) {
        List<RecipeIngredient> matchedIngredients = ingredientRepository.findByIngredient_NameIn(ingredients);


        Map<Recipe, Long> recipeCountMap = matchedIngredients.stream()
                .collect(Collectors.groupingBy(RecipeIngredient::getRecipe, Collectors.counting()));

        return recipeCountMap.entrySet().stream()
                .sorted(Map.Entry.<Recipe, Long>comparingByValue().reversed())
                .map(entry -> new RecipeDto(entry.getKey().getId(), entry.getKey().getName()))
                .collect(Collectors.toList());
    }
}
