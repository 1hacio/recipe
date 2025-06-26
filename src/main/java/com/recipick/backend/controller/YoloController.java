package com.recipick.backend.controller;

import com.recipick.backend.dto.RecipeDto;
import com.recipick.backend.service.RecipeService;
import com.recipick.backend.service.InventoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/yolo")
public class YoloController {

    private final RecipeService recipeService;
    private final InventoryService inventoryService;

    @PostMapping("/ingredients")
    public ResponseEntity<List<RecipeDto>> receiveYoloIngredients(
            @RequestBody YoloRequest request,
            @AuthenticationPrincipal OAuth2User user) {
        
        List<String> ingredients = request.getIngredients();
        String userEmail = user.getAttribute("email");

        // YOLO 결과를 재고에 등록
        inventoryService.registerYoloResults(ingredients, userEmail);

        // 서비스 호출로 레시피 추천 결과 얻기
        List<RecipeDto> recommended = recipeService.recommendRecipes(ingredients);

        return ResponseEntity.ok(recommended);
    }

    @Data
    static class YoloRequest {
        private List<String> ingredients;
    }
}
