package com.dia.springbootdeveloper.config.jwt;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.dia.springbootdeveloper.entity.User;
import com.dia.springbootdeveloper.repository.UserRepository;


@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;


    @Test
    void generateToken() {

        // given
        User testUser = userRepository.save(User.builder()
            .email("user@email.com")
            .password("test")
            .build());

        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then
        Long userId = tokenProvider.getUserId(token);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @Test
    void inValidToken() {

        // given
        String token = JwtFactory.builder()
            .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
            .build()
            .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }


    @Test
    void validToken_validToken() {
        // given
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);
        // when
        boolean result = tokenProvider.validToken(token);
        // then
        assertThat(result).isTrue();
    }

    @Test
    void getAuthentication() {
        // given
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
            .subject(userEmail)
            .build()
            .createToken(jwtProperties);
        // when
        Authentication authentication = tokenProvider.getAuthentication(token);
        // then
        assertThat(((UserDetails)authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @Test
    void getUserId() {
        // given
        Long userId = 1L;
        String token = JwtFactory.builder()
            .claims(Map.of("id",userId))
            .build()
            .createToken(jwtProperties);
        // when
        Long userIdByToken = tokenProvider.getUserId(token);
        // then
        assertThat(userIdByToken).isEqualTo(userId);
    }

}
