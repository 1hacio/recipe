package com.recipick.backend.controller;

import com.recipick.backend.model.User;
import com.recipick.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
public class UserController {

    private final UserRepository userRepository;

    // 로그인된 사용자 정보 조회
    @GetMapping
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }

        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");
        String pictureUrl = principal.getAttribute("picture");

        Optional<User> userOpt = userRepository.findByEmail(email);
        User user = userOpt.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setPictureUrl(pictureUrl);
            return userRepository.save(newUser);
        });

        return ResponseEntity.ok(Map.of(
                "authenticated", true,
                "user", Map.of(
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "name", user.getName(),
                        "picture", user.getPictureUrl()
                )
        ));
    }

    // 로그아웃 처리 (세션 무효화는 Spring Security에서 자동 처리)
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("success", true, "message", "로그아웃되었습니다."));
    }
}
