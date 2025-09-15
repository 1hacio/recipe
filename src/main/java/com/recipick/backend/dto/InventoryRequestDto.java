// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/dto/InventoryRequestDto.java

package com.recipick.backend.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class InventoryRequestDto {

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