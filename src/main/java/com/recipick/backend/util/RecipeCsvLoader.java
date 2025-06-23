package com.recipick.backend.util;

import com.recipick.backend.model.Recipe;
import com.recipick.backend.repository.RecipeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipeCsvLoader {

    private final RecipeRepository recipeRepository;

    @PostConstruct
    public void loadRecipes() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/recipe.csv"), StandardCharsets.UTF_8))) {

            // ✅ DB에서 기존 레시피 ID 목록 한 번만 조회
            Set<Long> existingIds = recipeRepository.findAll().stream()
                    .map(Recipe::getId)
                    .collect(Collectors.toSet());

            reader.lines().skip(1).forEach(line -> {
                try {
                    String[] fields = line.split(",", -1);
                    if (fields.length >= 5) {
                        Long id = Long.parseLong(fields[0]);

                        // ✅ 이미 존재하는 ID는 건너뜀
                        if (!existingIds.contains(id)) {
                            Recipe recipe = Recipe.builder()
                                    .id(id)
                                    .title(fields[1])
                                    .ingredients(fields[2])
                                    .description(fields[3])
                                    .imageUrl(fields[4])
                                    .build();

                            recipeRepository.save(recipe);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("❌ 레시피 한 줄 처리 중 에러: " + e.getMessage());
                }
            });

            System.out.println("✅ Recipe CSV 로드 및 저장 완료!");

        } catch (Exception e) {
            System.err.println("❌ Recipe CSV 전체 로드 중 에러:");
            e.printStackTrace();
        }
    }
}
