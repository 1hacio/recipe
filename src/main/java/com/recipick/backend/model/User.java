package com.recipick.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;

    public User(Long id, String email, String name, String pictureUrl) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;
    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastLogin = LocalDateTime.now();
    }


    // 구글 계정 이메일 (중복 X)drop
    @Column(unique = true, nullable = false)
    private String email;

    // 구글 계정 이름
    private String name;

    // 구글 프로필 이미지 URL (선택)
    private String pictureUrl;
}
