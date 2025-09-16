// 1hacio/recipe/recipe-0f6ad10d402de36580b03066c9b40cdf289fdae-3/src/main/java/com/recipick/backend/config/SecurityConfig.java

package com.recipick.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 보호 비활성화 (API 서버에서는 일반적으로 비활성화)
                .csrf(csrf -> csrf.disable())

                // 2. 모든 HTTP 요청에 대해 접근을 허용
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // 어떤 요청이든 인증 없이 접근 허용
                );

        return http.build();
    }
}