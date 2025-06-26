package com.recipick.backend.controller;

import com.recipick.backend.dto.InventoryRequestDto;
import com.recipick.backend.dto.InventoryResponseDto;
import com.recipick.backend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> getInventoryList(@RequestParam String userEmail) {
        return ResponseEntity.ok(inventoryService.getInventoryList(userEmail));
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDto> addInventory(@RequestBody InventoryRequestDto dto) {
        return ResponseEntity.ok(inventoryService.addInventory(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDto> updateInventory(@PathVariable Long id, @RequestBody InventoryRequestDto dto) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
