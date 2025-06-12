package com.recipick.backend.repository;

import com.recipick.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 이름 중복 체크용
    boolean existsByEmail(String email);
}
