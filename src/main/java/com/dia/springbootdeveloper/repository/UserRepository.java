package com.dia.springbootdeveloper.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.dia.springbootdeveloper.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
