package com.recipick.backend.util;

import com.recipick.backend.entity.Recipe;
import com.recipick.backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class RecipeCsvLoader {

    @Autowired
    private RecipeRepository recipeRepository;

    @PostConstruct
    public void loadRecipes() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/recipe.csv"), StandardCharsets.UTF_8))) {

            reader.lines().skip(1).forEach(line -> {
                String[] fields = line.split(",", -1); // 빈 문자열도 포함
                if (fields.length >= 5) {
                    Recipe recipe = new Recipe();
                    recipe.setId(Long.parseLong(fields[0]));
                    recipe.setTitle(fields[1]);
                    recipe.setIngredients(fields[2]);
                    recipe.setInstructions(fields[3]);
                    recipe.setImageUrl(fields[4]);
                    recipeRepository.save(recipe);
                }
            });

            System.out.println("Recipe 데이터 삽입 완료!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
