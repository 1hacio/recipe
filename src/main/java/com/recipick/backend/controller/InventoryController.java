package com.recipick.backend.controller;

import com.recipick.backend.dto.InventoryRequestDto;
import com.recipick.backend.dto.InventoryResponseDto;
import com.recipick.backend.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryResponseDto> getInventoryList() {
        return inventoryService.getInventoryList();
    }
    @PostMapping
    public void registerInventory(@RequestBody InventoryRequestDto dto) {
        inventoryService.registerInventory(dto);
    }

}
