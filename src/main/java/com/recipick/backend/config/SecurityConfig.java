package com.recipick.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Post 요청 가능하게 함
                .authorizeHttpRequests()
                .requestMatchers("/register", "/users", "/test").permitAll() // 여기에 추가!
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .oauth2Login(withDefaults())
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/loginSuccess") // 로그인 성공 시 이동할 경로
                );

        return http.build();
    }
}
