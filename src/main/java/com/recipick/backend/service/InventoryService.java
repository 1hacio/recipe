// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/service/InventoryService.java

package com.recipick.backend.service;

import com.recipick.backend.dto.InventoryRequestDto;
import com.recipick.backend.dto.InventoryResponseDto;
import com.recipick.backend.model.Inventory;
import com.recipick.backend.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // 모든 재고 목록을 조회하는 메서드
    @Transactional(readOnly = true)
    public List<InventoryResponseDto> getInventoryList() {
        return inventoryRepository.findAll().stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toList());
    }

    // 새로운 재고를 등록하는 메서드
    @Transactional
    public void registerInventory(InventoryRequestDto dto) {
        Inventory inventory = requestDtoToEntity(dto);
        inventoryRepository.save(inventory);
    }

    // --- 데이터 변환 헬퍼 메서드 ---

    // Request DTO를 Entity로 변환
    private Inventory requestDtoToEntity(InventoryRequestDto dto) {
        Inventory inventory = new Inventory();

        // Product 정보 복사
        if (dto.getProduct() != null) {
            inventory.setProductId(dto.getProduct().getProductId());
            inventory.setProductName(dto.getProduct().getName());
            if (dto.getProduct().getAliases() != null) {
                // List<String>을 콤마로 구분된 단일 문자열로 변환
                inventory.setAliases(String.join(",", dto.getProduct().getAliases()));
            }
        }

        // Amount 정보 복사
        if (dto.getAmount() != null) {
            inventory.setAmountType(dto.getAmount().getType());
            inventory.setAmountValue(dto.getAmount().getValue());
            inventory.setAmountUnit(dto.getAmount().getUnit());
            inventory.setAmountLevel(dto.getAmount().getLevel());
        }

        // 나머지 정보 복사
        inventory.setExpirationDate(dto.getExpirationDate());
        inventory.setMemo(dto.getMemo());
        inventory.setPurchaseDate(dto.getPurchaseDate());
        inventory.setImageUrl(dto.getImageUrl());

        return inventory;
    }

    // Entity를 Response DTO로 변환
    private InventoryResponseDto entityToResponseDto(Inventory inventory) {
        InventoryResponseDto dto = new InventoryResponseDto();
        dto.setId(inventory.getId()); // ID 포함

        // Product 정보 복사
        InventoryResponseDto.ProductDto productDto = new InventoryResponseDto.ProductDto();
        productDto.setProductId(inventory.getProductId());
        productDto.setName(inventory.getProductName());
        if (inventory.getAliases() != null && !inventory.getAliases().isEmpty()) {
            // 콤마로 구분된 단일 문자열을 List<String>으로 변환
            productDto.setAliases(List.of(inventory.getAliases().split(",")));
        }
        dto.setProduct(productDto);

        // Amount 정보 복사
        InventoryResponseDto.AmountDto amountDto = new InventoryResponseDto.AmountDto();
        amountDto.setType(inventory.getAmountType());
        amountDto.setValue(inventory.getAmountValue());
        amountDto.setUnit(inventory.getAmountUnit());
        amountDto.setLevel(inventory.getAmountLevel());
        dto.setAmount(amountDto);

        // 나머지 정보 복사
        dto.setExpirationDate(inventory.getExpirationDate());
        dto.setMemo(inventory.getMemo());
        dto.setPurchaseDate(inventory.getPurchaseDate());
        dto.setImageUrl(inventory.getImageUrl());

        return dto;
    }
}