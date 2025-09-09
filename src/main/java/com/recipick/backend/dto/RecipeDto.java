package com.recipick.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class RecipeDto {
    private String seq;
    private String name;
    private String link;
    private String image;
    private List<String> have;
    private List<String> missing;
    private String color;
}