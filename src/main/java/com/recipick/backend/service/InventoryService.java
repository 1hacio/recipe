package com.recipick.backend.service;

import com.recipick.backend.dto.InventoryRequestDto;
import com.recipick.backend.dto.InventoryResponseDto;
import com.recipick.backend.model.Inventory;
import com.recipick.backend.model.User;
import com.recipick.backend.repository.InventoryRepository;
import com.recipick.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository repository;
    private final UserRepository userRepository;

    public InventoryService(InventoryRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<InventoryResponseDto> getInventoryList(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userEmail));
        
        return repository.findByUser(user).stream()
                .map(i -> new InventoryResponseDto(i.getId(), i.getName(), i.getQuantity(), i.getExpireDate()))
                .collect(Collectors.toList());
    }
    
    public void registerInventory(InventoryRequestDto dto) {
        User user = userRepository.findByEmail(dto.getUserEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + dto.getUserEmail()));
        
        // 중복 체크
        Optional<Inventory> existingInventory = repository.findByUserAndName(user, dto.getName());
        
        if (existingInventory.isPresent()) {
            // 기존 재고가 있으면 수량 증가
            Inventory inventory = existingInventory.get();
            inventory.setQuantity(inventory.getQuantity() + dto.getQuantity());
            // 만료일이 더 늦은 것으로 업데이트
            if (dto.getExpireDate() != null && 
                (inventory.getExpireDate() == null || dto.getExpireDate().isAfter(inventory.getExpireDate()))) {
                inventory.setExpireDate(dto.getExpireDate());
            }
            repository.save(inventory);
        } else {
            // 새로운 재고 등록
            Inventory entity = new Inventory();
            entity.setName(dto.getName());
            entity.setQuantity(dto.getQuantity());
            entity.setExpireDate(dto.getExpireDate());
            entity.setUser(user);
            repository.save(entity);
        }
    }
    
    // YOLO 결과를 받아서 재고에 등록
    public void registerYoloResults(List<String> ingredients, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userEmail));
        
        for (String ingredient : ingredients) {
            // 중복 체크
            Optional<Inventory> existingInventory = repository.findByUserAndName(user, ingredient);
            
            if (existingInventory.isPresent()) {
                // 기존 재고가 있으면 수량 증가
                Inventory inventory = existingInventory.get();
                inventory.setQuantity(inventory.getQuantity() + 1);
                repository.save(inventory);
            } else {
                // 새로운 재고 등록 (기본 수량 1, 만료일은 7일 후로 설정)
                Inventory entity = new Inventory();
                entity.setName(ingredient);
                entity.setQuantity(1);
                entity.setExpireDate(java.time.LocalDate.now().plusDays(7));
                entity.setUser(user);
                repository.save(entity);
            }
        }
    }
}
