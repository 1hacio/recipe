package com.recipick.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthUserController {

    @GetMapping("/api/user")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal OAuth2User oauthUser) {
        if (oauthUser == null) {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }

        Map<String, Object> userInfo = Map.of(
                "authenticated", true,
                "user", Map.of(
                        "email", oauthUser.getAttribute("email"),
                        "name", oauthUser.getAttribute("name"),
                        "picture", oauthUser.getAttribute("picture")
                )
        );
        return ResponseEntity.ok(userInfo);
    }
}
