package com.recipick.backend.controller;

import com.recipick.backend.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String period // 예: "7일", "당일", "30일"
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Map<String, Object> stats = mypageService.getStats(start, end, period);
        return ResponseEntity.ok(stats);
    }
}
