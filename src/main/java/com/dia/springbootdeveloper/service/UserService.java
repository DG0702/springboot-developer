package com.dia.springbootdeveloper.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dia.springbootdeveloper.dto.request.AddUserRequest;
import com.dia.springbootdeveloper.entity.User;
import com.dia.springbootdeveloper.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long save(AddUserRequest request) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
            .email(request.getEmail())
            .password(encoder.encode(request.getPassword()))
            .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("UnExcepted user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("UnExcepted user"));
    }
}
