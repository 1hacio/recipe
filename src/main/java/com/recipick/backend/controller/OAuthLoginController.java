package com.recipick.backend.controller;

import com.recipick.backend.model.User;
import com.recipick.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@RestController
public class OAuthLoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginSuccess")
    public String loginSuccess(Authentication authentication) {
        // 로그인된 사용자 정보 가져오기
        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");
        String picture = user.getAttribute("picture");

        // DB에 사용자 정보 없으면 저장
        if (email != null && !userRepository.existsByEmail(email)) {
            userRepository.save(new User(null, email, name, picture));
        }

        return "로그인 성공! 사용자: " + email;
    }
    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes(); // email, name, picture 등
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return ResponseEntity.status(401).body("로그인되지 않은 사용자입니다.");
        }

        Map<String, Object> userInfo = Map.of(
                "email", user.getAttribute("email"),
                "name", user.getAttribute("name"),
                "picture", user.getAttribute("picture")
        );

        return ResponseEntity.ok(userInfo);
    }


}
