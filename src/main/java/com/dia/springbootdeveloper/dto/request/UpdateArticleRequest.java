package com.dia.springbootdeveloper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class UpdateArticleRequest {

    private String title;
    private String content;
}
