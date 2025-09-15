// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/dto/GptRequestDto.java

package com.recipick.backend.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class GptRequestDto {
    private List<String> ingredients;
    private String mode; // "current" or "desired"
    private String desiredInput;
    private Modifiers modifiers;

    @Data
    public static class Modifiers {
        private String meal; // 아침, 점심, 저녁
        private Boolean weather;
        private String weatherType; // 더위, 맑음, 비, 눈
        private Map<String, Boolean> diet; // lowSodium, lowFat 등
        private String spicy; // 순한, 보통, 매운
        private String time; // 15, 30, 60
        private Integer servings;
        private List<String> cuisine; // 한식, 양식 등
        private Map<String, Boolean> tools; // airfryer, noOven
        private Boolean strict;
    }
}