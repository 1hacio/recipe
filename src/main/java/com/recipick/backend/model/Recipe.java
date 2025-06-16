package com.recipick.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @Column(name = "rcp_sno")
    private Long id;  // 레시피 일련번호 (CSV 기준)

    @Column(name = "rcp_ttl", nullable = false)
    private String title;  // 레시피 제목

    @Column(name = "ckg_nm")
    private String alias;  // 짧은 이름, 별칭

    @Column(name = "rgtr_id")
    private String registrantId;  // 게시자 ID

    @Column(name = "rgtr_nm")
    private String registrantName;  // 게시자 이름

    @Column(name = "inq_cnt")
    private Integer viewCount;

    @Column(name = "rcmm_cnt")
    private Integer recommendCount;

    @Column(name = "srap_cnt")
    private Integer scrapCount;

    @Column(name = "ckg_mth_acto_nm")
    private String cookingMethod;

    @Column(name = "ckg_sta_acto_nm")
    private String cookingSituation;

    @Column(name = "ckg_mtrl_acto_nm")
    private String mainIngredient;

    @Column(name = "ckg_knd_acto_nm")
    private String dishType;

    @Column(name = "ckg_ipdc", columnDefinition = "TEXT")
    private String description;

    @Column(name = "ckg_mtrl_cn", columnDefinition = "TEXT")
    private String ingredients;  // 재료 + 분량 (CSV 그대로)

    @Column(name = "ckg_inbun_nm")
    private String servings;

    @Column(name = "ckg_dodf_nm")
    private String difficulty;

    @Column(name = "ckg_time_nm")
    private String cookingTime;

    @Column(name = "first_reg_dt")
    private LocalDateTime firstRegisteredAt;

    @Column(name = "rcp_img_url", length = 600)
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
