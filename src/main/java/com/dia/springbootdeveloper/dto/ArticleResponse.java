package com.dia.springbootdeveloper.dto;

import com.dia.springbootdeveloper.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleResponse {
    private String title;
    private String content;

    public ArticleResponse (Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }

    // public static ArticleResponse from(Article article) {
    //     ArticleResponse response = new ArticleResponse(article);
    //
    //     response.title = article.getTitle();
    //     response.content = article.getContent();
    //
    //     return response;
    // }
}
