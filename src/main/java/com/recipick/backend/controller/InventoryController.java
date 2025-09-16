// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/controller/InventoryController.java

package com.recipick.backend.controller;

import com.recipick.backend.dto.InventoryRequestDto;
import com.recipick.backend.dto.InventoryResponseDto;
import com.recipick.backend.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // 모든 재고 목록을 조회
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> getInventoryList() {
        List<InventoryResponseDto> inventoryList = inventoryService.getInventoryList();
        return ResponseEntity.ok(inventoryList);
    }

    // 새로운 재고를 등록
    @PostMapping
    public ResponseEntity<Void> registerInventory(@RequestBody InventoryRequestDto dto) {
        inventoryService.registerInventory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<Void> updateInventory(@PathVariable Long id, @RequestBody InventoryRequestDto dto) {
        // inventoryService.updateInventory(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        // inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

}