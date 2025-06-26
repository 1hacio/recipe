package com.recipick.backend.service;

import com.recipick.backend.dto.InventoryRequestDto;
import com.recipick.backend.dto.InventoryResponseDto;
import com.recipick.backend.model.Inventory;
import com.recipick.backend.model.User;
import com.recipick.backend.repository.InventoryRepository;
import com.recipick.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;
    private final UserRepository userRepository;

    public List<InventoryResponseDto> getInventoryList(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userEmail));

        return repository.findByUser(user).stream()
                .map(i -> new InventoryResponseDto(
                        i.getId(),
                        i.getName(),
                        i.getAmountType(),
                        i.getCountValue(),
                        i.getStepLevel(),
                        i.getExactValue(),
                        i.getExactUnit(),
                        i.getPurchaseDate(),
                        i.getExpireDate(),
                        i.getMemo()
                ))
                .collect(Collectors.toList());
    }

    public InventoryResponseDto addInventory(InventoryRequestDto dto) {
        User user = userRepository.findByEmail(dto.getUserEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + dto.getUserEmail()));

        Inventory entity = new Inventory();
        entity.setUser(user);
        entity.setName(dto.getName());
        entity.setAmountType(dto.getAmountType());
        entity.setCountValue(dto.getCountValue());
        entity.setStepLevel(dto.getStepLevel());
        entity.setExactValue(dto.getExactValue());
        entity.setExactUnit(dto.getExactUnit());
        entity.setPurchaseDate(dto.getPurchaseDate());
        entity.setExpireDate(dto.getExpireDate());
        entity.setMemo(dto.getMemo());

        Inventory saved = repository.save(entity);

        return new InventoryResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getAmountType(),
                saved.getCountValue(),
                saved.getStepLevel(),
                saved.getExactValue(),
                saved.getExactUnit(),
                saved.getPurchaseDate(),
                saved.getExpireDate(),
                saved.getMemo()
        );
    }

    public InventoryResponseDto updateInventory(Long id, InventoryRequestDto dto) {
        Inventory entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("재고를 찾을 수 없습니다. id=" + id));

        entity.setName(dto.getName());
        entity.setAmountType(dto.getAmountType());
        entity.setCountValue(dto.getCountValue());
        entity.setStepLevel(dto.getStepLevel());
        entity.setExactValue(dto.getExactValue());
        entity.setExactUnit(dto.getExactUnit());
        entity.setPurchaseDate(dto.getPurchaseDate());
        entity.setExpireDate(dto.getExpireDate());
        entity.setMemo(dto.getMemo());

        Inventory updated = repository.save(entity);

        return new InventoryResponseDto(
                updated.getId(),
                updated.getName(),
                updated.getAmountType(),
                updated.getCountValue(),
                updated.getStepLevel(),
                updated.getExactValue(),
                updated.getExactUnit(),
                updated.getPurchaseDate(),
                updated.getExpireDate(),
                updated.getMemo()
        );
    }

    public void deleteInventory(Long id) {
        repository.deleteById(id);
    }

    // 여기서부터 추가된 YOLO 결과 수신 메서드
    public void registerYoloResults(List<String> ingredients, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userEmail));

        for (String ingredient : ingredients) {
            Optional<Inventory> existingInventory = repository.findByUserAndName(user, ingredient);

            if (existingInventory.isPresent()) {
                Inventory inventory = existingInventory.get();
                Integer currentCount = inventory.getCountValue() == null ? 0 : inventory.getCountValue();
                inventory.setCountValue(currentCount + 1);
                inventory.setAmountType("count");
                repository.save(inventory);
            } else {
                Inventory entity = new Inventory();
                entity.setName(ingredient);
                entity.setAmountType("count");
                entity.setCountValue(1);
                entity.setExpireDate(LocalDate.now().plusDays(7));
                entity.setUser(user);
                repository.save(entity);
            }
        }
    }
}
