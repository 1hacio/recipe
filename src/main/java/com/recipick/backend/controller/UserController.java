package com.recipick.backend.controller;

import com.recipick.backend.model.User;
import com.recipick.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getName())) {
            return "❌ 이미 존재하는 사용자입니다.";
        }
        userRepository.save(user);  // ✅ DB에 저장
        return "✅ 회원가입 성공: " + user.getName();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
