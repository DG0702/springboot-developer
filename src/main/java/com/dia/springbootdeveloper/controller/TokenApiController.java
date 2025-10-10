package com.dia.springbootdeveloper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dia.springbootdeveloper.dto.request.CreateAccessTokenRequest;
import com.dia.springbootdeveloper.dto.response.CreateAccessTokenResponse;
import com.dia.springbootdeveloper.service.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
        @RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
