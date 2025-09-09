package com.recipick.backend.controller;

import java.util.List;

import com.recipick.backend.dto.RecipeDto;
import com.recipick.backend.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    // 1. 일반 추천 (입력 재료 기반)
    @PostMapping("/recommend")
    public List<RecipeDto> recommend(@RequestBody List<String> ingredients) {
        return recipeService.recommendRecipes(ingredients);
    }

    // 2. 로그인 사용자의 재고 기반 추천
    @GetMapping("/auto-recommend")
    public List<RecipeDto> autoRecommend(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        String userEmail = user.getAttribute("email");
        return recipeService.recommendByInventory(userEmail);
    }
}
