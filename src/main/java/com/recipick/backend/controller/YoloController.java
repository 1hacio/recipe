// 📁 controller/YoloController.java
package com.recipick.backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/yolo")
public class YoloController {

    // YOLO가 보낸 재료명을 받아 출력만 해보기
    @PostMapping("/result")
    public void receiveYoloResult(@RequestBody List<String> ingredients) {
        System.out.println("YOLO 감지 결과: " + ingredients);
        // TODO: 이걸로 레시피 추천 등에 활용 가능
    }
}
