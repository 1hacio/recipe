package com.recipick.backend.dto;

import java.time.LocalDate;

public class InventoryResponseDto {
    private Long id;
    private String name;
    private String amountType;
    private Integer countValue;
    private String stepLevel;
    private Double exactValue;
    private String exactUnit;
    private LocalDate purchaseDate;
    private LocalDate expireDate;
    private String memo;

    // 기본 생성자
    public InventoryResponseDto() {}

    // 모든 필드 포함하는 생성자
    public InventoryResponseDto(Long id, String name, String amountType, Integer countValue, String stepLevel,
                                Double exactValue, String exactUnit, LocalDate purchaseDate,
                                LocalDate expireDate, String memo) {
        this.id = id;
        this.name = name;
        this.amountType = amountType;
        this.countValue = countValue;
        this.stepLevel = stepLevel;
        this.exactValue = exactValue;
        this.exactUnit = exactUnit;
        this.purchaseDate = purchaseDate;
        this.expireDate = expireDate;
        this.memo = memo;
    }

    // getter / setter 전부 작성
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAmountType() { return amountType; }
    public void setAmountType(String amountType) { this.amountType = amountType; }

    public Integer getCountValue() { return countValue; }
    public void setCountValue(Integer countValue) { this.countValue = countValue; }

    public String getStepLevel() { return stepLevel; }
    public void setStepLevel(String stepLevel) { this.stepLevel = stepLevel; }

    public Double getExactValue() { return exactValue; }
    public void setExactValue(Double exactValue) { this.exactValue = exactValue; }

    public String getExactUnit() { return exactUnit; }
    public void setExactUnit(String exactUnit) { this.exactUnit = exactUnit; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public LocalDate getExpireDate() { return expireDate; }
    public void setExpireDate(LocalDate expireDate) { this.expireDate = expireDate; }

    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }
}
