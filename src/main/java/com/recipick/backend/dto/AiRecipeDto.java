package com.recipick.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class AiRecipeDto {
    private String 이름;
    private IngredientsDto 재료;
    private List<String> 레시피;
    private List<String> 키워드;

    @Data
    public static class IngredientsDto {
        private List<String> 보유재료;
        private List<String> 추가추천재료;
    }
}