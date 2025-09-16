// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/dto/GeminiRequestDto.java

package com.recipick.backend.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class GeminiRequestDto {
    private List<String> ingredients;
    private String mode; // "current" or "desired"
    private String desiredInput;
    private String modifiers;

}