// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/model/RecipeIngredient.java

package com.recipick.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // 이 부분이 수정되었습니다. 'rcp_sno'를 'recipe_id'로 변경하고,
    // 'Recipe' 엔티티의 'id' 컬럼을 참조하도록 명시합니다.
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "irdnt_sno")
    private Ingredient ingredient;
}