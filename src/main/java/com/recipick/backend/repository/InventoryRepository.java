package com.recipick.backend.repository;

import com.recipick.backend.model.Inventory;
import com.recipick.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByUser(User user);
    Optional<Inventory> findByUserAndName(User user, String name);
}
