// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/model/Inventory.java

package com.recipick.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Product 정보
    private String productId;
    @Column(name = "product_name") // DB의 name은 예약어일 수 있으므로 컬럼명 지정
    private String productName;
    // 'aliases'는 리스트 형태이므로 별도의 테이블로 처리하거나, 간단하게 콤마로 구분된 문자열로 저장할 수 있습니다.
    // 여기서는 간단한 문자열 방식을 택하겠습니다.
    private String aliases;

    // Amount 정보
    private String amountType; // "count", "exact", "step", "free"
    private Integer amountValue;
    private String amountUnit;
    private String amountLevel;

    // 기타 정보
    private LocalDate expirationDate;
    @Lob // 긴 텍스트를 위한 설정
    private String memo;
    private LocalDate purchaseDate;
    private String imageUrl;

}