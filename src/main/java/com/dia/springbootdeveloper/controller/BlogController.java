package com.dia.springbootdeveloper.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dia.springbootdeveloper.dto.request.AddArticleRequest;
import com.dia.springbootdeveloper.dto.response.ArticleResponse;
import com.dia.springbootdeveloper.dto.request.UpdateArticleRequest;
import com.dia.springbootdeveloper.entity.Article;
import com.dia.springbootdeveloper.service.BlogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        Article save = blogService.save(request,principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> all = blogService.findAll()
            .stream()
            .map(ArticleResponse::new)
            .toList();

        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    @GetMapping("/api/article/{id}")
    public ResponseEntity<ArticleResponse> findArticleById(@PathVariable Long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deletedArticle(@PathVariable Long id) {
        blogService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArtilce(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        Article article = blogService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(new ArticleResponse(article));
    }
}
