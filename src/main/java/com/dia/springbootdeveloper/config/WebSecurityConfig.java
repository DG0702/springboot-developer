// package com.dia.springbootdeveloper.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
//
// import com.dia.springbootdeveloper.service.UserDetailService;
//
// import lombok.RequiredArgsConstructor;
//
// @Configuration
// @EnableWebSecurity
// @RequiredArgsConstructor
// public class WebSecurityConfig {
//
//     private final UserDetailService userDetailsService;
//
//     @Bean
//     public WebSecurityCustomizer configure() {
//         return (web) -> web.ignoring()
//             // .requestMatchers(toH2Console())
//             .requestMatchers(("/static/**"));
//     }
//
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         return http
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/login", "/signup", "/user").permitAll()
//                 .anyRequest().authenticated())
//
//             .formLogin(formLogin -> formLogin
//                 .loginPage("/login")
//                 .defaultSuccessUrl("/articles"))
//             .logout(logout -> logout
//                 .logoutSuccessUrl("/login")
//                 .invalidateHttpSession(true))
//             .csrf(AbstractHttpConfigurer::disable)
//             .build();
//     }
//
//     @Bean
//     public AuthenticationManager authenticationManager(
//         HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
//         AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//
//         authBuilder
//             .userDetailsService(userDetailsService)
//             .passwordEncoder(bCryptPasswordEncoder);
//
//         return authBuilder.build();
//     }
//
//
//     @Bean
//     public BCryptPasswordEncoder bCryptPasswordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
//
// }
