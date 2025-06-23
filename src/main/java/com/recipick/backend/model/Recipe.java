package com.recipick.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Recipe {

    @Id
    @Column(name = "rcp_sno")
    private Long id;

    @Column(name = "rcp_ttl")
    private String title;

    @Column(name = "name")
    private String name;

    // 다른 필드들도 필요한 만큼 추가 가능

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients;
}
