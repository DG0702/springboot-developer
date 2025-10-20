package com.dia.springbootdeveloper.dto.request;

import com.dia.springbootdeveloper.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {

    private String title;
    private String content;

    public Article from(String author){
        return Article.builder()
            .title(title)
            .content(content)
            .author(author)
            .build();
    }
}
