package com.recipick.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InventoryResponseDto {
    private Long id;
    private String name;
    private int quantity;
    private LocalDate expireDate;
}
