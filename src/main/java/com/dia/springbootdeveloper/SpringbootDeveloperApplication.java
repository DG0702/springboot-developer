package com.dia.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.dia.springbootdeveloper.config.jwt.JwtProperties;

@EnableJpaAuditing
@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class SpringbootDeveloperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDeveloperApplication.class, args);
    }

}
