// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/util/RecipeCsvLoader.java

package com.recipick.backend.util;

import com.recipick.backend.model.Recipe;
import com.recipick.backend.repository.RecipeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class RecipeCsvLoader {

    @Autowired
    private RecipeRepository recipeRepository;

    @PostConstruct
    public void loadRecipes() {
        // DB에 데이터가 이미 있으면 실행하지 않음
        if (recipeRepository.count() > 0) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("recipe.csv").getInputStream(), StandardCharsets.UTF_8))) {

            String line = reader.readLine(); // 헤더 라인 건너뛰기
            while ((line = reader.readLine()) != null) {
                // CSV 파싱 시 콤마를 포함하는 필드를 고려하여 정규식 사용
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                Recipe recipe = new Recipe();
                recipe.setRecipeName(data[0].replace("\"", ""));
                recipe.setIngredients(data[1].replace("\"", ""));
                recipe.setCategory(data[2].replace("\"", ""));
                recipe.setCalorie(data[3].replace("\"", ""));
                recipe.setProtein(data[4].replace("\"", ""));
                recipe.setFat(data[5].replace("\"", ""));
                recipe.setSodium(data[6].replace("\"", ""));
                recipe.setImageUrl(data[7].replace("\"", ""));
                recipe.setStep1(data.length > 8 ? data[8].replace("\"", "") : null);
                recipe.setStep2(data.length > 9 ? data[9].replace("\"", "") : null);
                recipe.setStep3(data.length > 10 ? data[10].replace("\"", "") : null);
                recipe.setStep4(data.length > 11 ? data[11].replace("\"", "") : null);
                recipe.setStep5(data.length > 12 ? data[12].replace("\"", "") : null);
                recipe.setStep6(data.length > 13 ? data[13].replace("\"", "") : null);
                recipe.setStep7(data.length > 14 ? data[14].replace("\"", "") : null);
                recipe.setStep8(data.length > 15 ? data[15].replace("\"", "") : null);
                recipe.setStep9(data.length > 16 ? data[16].replace("\"", "") : null);
                recipe.setStep10(data.length > 17 ? data[17].replace("\"", "") : null);

                recipeRepository.save(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}