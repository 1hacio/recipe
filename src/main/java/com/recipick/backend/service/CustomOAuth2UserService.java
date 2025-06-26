package com.recipick.backend.service;

import com.recipick.backend.model.User;
import com.recipick.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String pictureUrl = oauth2User.getAttribute("picture");
        
        // DB에서 사용자 조회 또는 생성
        Optional<User> userOpt = userRepository.findByEmail(email);
        User user;
        
        if (userOpt.isEmpty()) {
            // 새 사용자 생성
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPictureUrl(pictureUrl);
            userRepository.save(user);
        } else {
            // 기존 사용자 업데이트 (마지막 로그인 시간 등)
            user = userOpt.get();
            user.setName(name);
            user.setPictureUrl(pictureUrl);
            userRepository.save(user);
        }
        
        return oauth2User;
    }
} 