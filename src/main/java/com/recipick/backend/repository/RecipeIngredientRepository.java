package com.recipick.backend.repository;

import com.recipick.backend.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    List<RecipeIngredient> findByIngredient_NameIn(List<String> names);
}
