package com.recipick.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecipeDto {
    private Long id;
    private String name;
    private String description;
    private String cookingTime;
    private String difficulty;
    private String imageUrl;
    private List<String> ingredients;
}
