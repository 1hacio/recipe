// 1hacio/recipe/recipe-0f6ad1_0d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/dto/InventoryResponseDto.java

package com.recipick.backend.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class InventoryResponseDto {

    // 재고 아이템의 고유 ID를 추가해주는 것이 좋습니다. (수정, 삭제 시 필요)
    private Long id;

    private ProductDto product;
    private AmountDto amount;
    private LocalDate expirationDate;
    private String memo;
    private LocalDate purchaseDate;
    private String imageUrl;

    @Data
    public static class ProductDto {
        private String productId;
        private String name;
        private List<String> aliases;
    }

    @Data
    public static class AmountDto {
        private String type; // "count", "exact", "step", "free"
        private Integer value;
        private String unit;
        private String level;
    }
}