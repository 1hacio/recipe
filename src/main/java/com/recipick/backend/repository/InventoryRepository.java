package com.recipick.backend.repository;

import com.recipick.backend.model.Inventory;
import com.recipick.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    // 사용자별 재고 목록 조회
    List<Inventory> findByUser(User user);
    
    // 사용자와 재료명으로 중복 체크
    Optional<Inventory> findByUserAndName(User user, String name);
    
    // 사용자와 재료명으로 존재 여부 확인
    boolean existsByUserAndName(User user, String name);
}
