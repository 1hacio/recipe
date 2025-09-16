// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae3/src/main/java/com/recipick/backend/repository/InventoryRepository.java

package com.recipick.backend.repository;

import com.recipick.backend.model.Inventory;
import com.recipick.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // 이 메서드는 User 엔티티와 연관 관계가 필요하므로,
    // 사용자 기능 구현 전까지 잠시 주석 처리하거나 삭제합니다.
    // Optional<Inventory> findByUserAndName(User user, String name);

    // 이 메서드도 User 엔티티가 필요하므로 주석 처리합니다.
    // List<Inventory> findAllByUser(User user);
}