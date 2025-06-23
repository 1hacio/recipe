package com.recipick.backend.service;

import com.recipick.backend.dto.InventoryRequestDto;
import com.recipick.backend.dto.InventoryResponseDto;
import com.recipick.backend.model.Inventory;
import com.recipick.backend.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<InventoryResponseDto> getInventoryList() {
        return repository.findAll().stream()
                .map(i -> new InventoryResponseDto(i.getId(), i.getName(), i.getQuantity(), i.getExpireDate()))
                .collect(Collectors.toList());
    }
    public void registerInventory(InventoryRequestDto dto) {
        Inventory entity = new Inventory();
        entity.setName(dto.getName());
        entity.setQuantity(dto.getQuantity());
        entity.setExpireDate(dto.getExpireDate());
        repository.save(entity);
    }

}
