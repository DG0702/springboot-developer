package com.dia.springbootdeveloper.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dia.springbootdeveloper.config.jwt.TokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final String Header_Authorization = "Authorization";
    private final String Token_Prefix = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(Header_Authorization);
        String token = getAccessToken(authorizationHeader);

        if(tokenProvider.validToken(token)) {
            tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().getAuthentication();
        }

        filterChain.doFilter(request,response);
    }

    private String getAccessToken(String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith(Token_Prefix)) {
            return authorizationHeader.substring(Token_Prefix.length());
        }
        return null;
    }
}
