package com.recipick.backend.controller;

import com.recipick.backend.dto.InventoryRequestDto;
import com.recipick.backend.dto.InventoryResponseDto;
import com.recipick.backend.service.InventoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryResponseDto> getInventoryList(@AuthenticationPrincipal OAuth2User user) {
        String userEmail = user.getAttribute("email");
        return inventoryService.getInventoryList(userEmail);
    }
    
    @PostMapping
    public void registerInventory(@RequestBody InventoryRequestDto dto, @AuthenticationPrincipal OAuth2User user) {
        String userEmail = user.getAttribute("email");
        dto.setUserEmail(userEmail);
        inventoryService.registerInventory(dto);
    }

    @PostMapping("/yolo")
    public void registerYoloIngredients(@RequestBody List<String> ingredients, @AuthenticationPrincipal OAuth2User user) {
        String userEmail = user.getAttribute("email");
        inventoryService.registerYoloResults(ingredients, userEmail);
    }
}
