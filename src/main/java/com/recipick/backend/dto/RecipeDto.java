package com.recipick.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeDto {
    private Long id;
    private String name;
}
