// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/model/Recipe.java

package com.recipick.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipeName; // 요리 이름

    @Lob
    @Column(length = 1000) // 재료 문자열 길이를 고려해 충분한 길이 할당
    private String ingredients; // 재료 (콤마로 구분된 문자열)

    private String category; // 요리 분류

    private String calorie;
    private String protein;
    private String fat;
    private String sodium;

    private String imageUrl; // 이미지 URL

    // 레시피 조리법 (1~10단계)
    @Lob
    @Column(length = 2000)
    private String step1;
    @Lob
    @Column(length = 2000)
    private String step2;
    @Lob
    @Column(length = 2000)
    private String step3;
    @Lob
    @Column(length = 2000)
    private String step4;
    @Lob
    @Column(length = 2000)
    private String step5;
    @Lob
    @Column(length = 2000)
    private String step6;
    @Lob
    @Column(length = 2000)
    private String step7;
    @Lob
    @Column(length = 2000)
    private String step8;
    @Lob
    @Column(length = 2000)
    private String step9;
    @Lob
    @Column(length = 2000)
    private String step10;
}