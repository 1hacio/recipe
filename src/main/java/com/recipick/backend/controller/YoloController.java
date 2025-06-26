package com.recipick.backend.controller;

import com.recipick.backend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/yolo")
public class YoloController {

    private final InventoryService inventoryService;

    // YOLO 인식 결과를 받아 재고 등록 처리
    @PostMapping("/result")
    public ResponseEntity<Void> receiveYoloResult(@RequestParam String userEmail,
                                                  @RequestBody List<String> ingredients) {
        inventoryService.registerYoloResults(ingredients, userEmail);
        return ResponseEntity.ok().build();
    }
}
