package com.dia.springbootdeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dia.springbootdeveloper.entity.Article;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
