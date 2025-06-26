package com.recipick.backend.repository;

import com.recipick.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 이름 중복 체크용
    boolean existsByEmail(String email);
    
    // 이메일로 사용자 조회
    Optional<User> findByEmail(String email);
}
