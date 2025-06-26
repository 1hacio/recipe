package com.recipick.backend.dto;

import java.util.List;

public class RecipeDto {
    private Long id;
    private String title;           // entity.getTitle()
    private String description;
    private String cookingTime;
    private String difficulty;
    private String imageUrl;
    private List<String> ingredients;

    public RecipeDto() {}

    public RecipeDto(Long id, String title, String description, String cookingTime,
                     String difficulty, String imageUrl, List<String> ingredients) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cookingTime = cookingTime;
        this.difficulty = difficulty;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
    }

    // getter, setter 생략 가능하지만 필요하면 추가하세요
    // ...
}
