package com.recipick.backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InventoryRequestDto {
    private String name;
    private int quantity;
    private LocalDate expireDate;
    private String userEmail;
}
