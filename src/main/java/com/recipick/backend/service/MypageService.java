package com.recipick.backend.service;

import com.recipick.backend.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final InventoryRepository inventoryRepository;

    public Map<String, Object> getStats(LocalDate start, LocalDate end, String period) {
        // 예시로 임의 데이터 리턴 (실제 구현 시 DB에서 쿼리 후 가공)
        Map<String, Object> result = new HashMap<>();

        List<Map<String, Object>> stats = List.of(
                Map.of("label", "추가한 식자재 수", "value", 49, "icon", "🛒"),
                Map.of("label", "구매한 식자재 수", "value", 26, "icon", "✔️"),
                Map.of("label", "사용한 식자재 수", "value", 11, "icon", "🍴"),
                Map.of("label", "폐기한 식자재 수", "value", 2, "icon", "🗑️"),
                Map.of("label", "남은 식자재 수", "value", 36, "icon", "📦")
        );

        String popularItems = "토마토, 파스타 면";

        result.put("stats", stats);
        result.put("popularItems", popularItems);

        return result;
    }
}
