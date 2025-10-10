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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest request) {
        return userRepository.save(User.builder()
            .email(request.getEmail())
            .password(bCryptPasswordEncoder.encode(request.getPassword()))
            .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("UnExcepted user"));
    }
}
