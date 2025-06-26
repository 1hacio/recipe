package com.recipick.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {
    
    @GetMapping("/test")
    public String test() {
        return "서버 정상 작동!";
    }
    
    @GetMapping("/test/json")
    public ResponseEntity<Map<String, Object>> testJson() {
        return ResponseEntity.ok(Map.of(
            "message", "JSON 응답 테스트",
            "status", "success",
            "timestamp", System.currentTimeMillis()
        ));
    }
    
    @PostMapping("/test/post")
    public ResponseEntity<Map<String, Object>> testPost(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(Map.of(
            "message", "POST 요청 테스트 성공",
            "receivedData", request,
            "status", "success"
        ));
    }
    
    @GetMapping("/test/auth")
    public ResponseEntity<Map<String, Object>> testAuth() {
        return ResponseEntity.ok(Map.of(
            "message", "인증 테스트",
            "authenticated", true,
            "status", "success"
        ));
    }
}