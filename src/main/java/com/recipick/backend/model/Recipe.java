package com.recipick.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "rcp_sno")
    private Long id;

    @Column(name = "rcp_ttl", nullable = false)
    private String title;

    @Column(name = "ckg_nm")
    private String alias;

    @Column(name = "rgtr_id")
    private String registrantId;

    @Column(name = "rgtr_nm")
    private String registrantName;

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
    private String ingredients;

    @Column(name = "ckg_inbun_nm")
    private String servings;

    @Column(name = "ckg_dodf_nm")
    private String difficulty;

    @Column(name = "ckg_time_nm")
    private String cookingTime;

    @Column(name = "rcp_img_url", length = 600)
    private String imageUrl;

    @Column(name = "first_reg_dt")
    private LocalDateTime firstRegisteredAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference
    private List<RecipeIngredient> recipeIngredients;

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
