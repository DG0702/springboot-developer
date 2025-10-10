package com.dia.springbootdeveloper.service;

import org.springframework.stereotype.Service;

import com.dia.springbootdeveloper.entity.RefreshToken;
import com.dia.springbootdeveloper.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
