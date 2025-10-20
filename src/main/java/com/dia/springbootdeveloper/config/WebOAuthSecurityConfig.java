package com.dia.springbootdeveloper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import com.dia.springbootdeveloper.config.jwt.TokenProvider;
import com.dia.springbootdeveloper.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.dia.springbootdeveloper.config.oauth.OAuth2SuccessHandler;
import com.dia.springbootdeveloper.config.oauth.OAuth2UserCustomService;
import com.dia.springbootdeveloper.repository.RefreshTokenRepository;
import com.dia.springbootdeveloper.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {
    private final OAuth2UserCustomService oAuth2CustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
            .requestMatchers("/img/**")
            .requestMatchers("/css/**")
            .requestMatchers("/js/**");
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/token").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
            .oauth2Login(oauth2 -> oauth2.loginPage("/login")
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.authorizationRequestRepository(
                    oAuth2AuthorizationRequestBasedOnCookieRepository()))
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(oAuth2CustomService))
                .successHandler(oAuth2SuccessHandler()))
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    new RequestHeaderRequestMatcher("/api/**")
                ))
            .build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return
            new OAuth2SuccessHandler(tokenProvider, refreshTokenRepository,
            oAuth2AuthorizationRequestBasedOnCookieRepository(), userService);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
