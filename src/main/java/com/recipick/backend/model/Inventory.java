package com.recipick.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String amountType;      // count, step, exact
    private Integer countValue;
    private String stepLevel;
    private Double exactValue;
    private String exactUnit;
    private LocalDate purchaseDate;
    private LocalDate expireDate;
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
