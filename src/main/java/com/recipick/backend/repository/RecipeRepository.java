package com.recipick.backend.repository;

import com.recipick.backend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // ingredients 컬럼에서 주어진 재료 이름(keyword)을 포함하는 레시피를 검색하는 메서드
    List<Recipe> findByIngredientsContaining(String keyword);
}