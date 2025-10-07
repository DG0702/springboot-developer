package com.dia.springbootdeveloper.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dia.springbootdeveloper.dto.request.AddArticleRequest;
import com.dia.springbootdeveloper.dto.request.UpdateArticleRequest;
import com.dia.springbootdeveloper.entity.Article;
import com.dia.springbootdeveloper.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BlogRepository blogRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest request = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions result = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAll")
    @Test
    public void findAll() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Article.builder()
            .title(title)
            .content(content)
            .build());

        // when
        ResultActions result = mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON));

        // then
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value(title))
            .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("findById")
    @Test
    public void findArticle() throws Exception {
        // given
        final String url = "/api/article/{id}";
        final String title = "title";
        final String content = "content";

        Article article = Article.builder()
            .title(title)
            .content(content)
            .build();

        Article save = blogRepository.save(article);

        // when
        final ResultActions result = mockMvc.perform(get(url, save.getId()));

        // then
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(title))
            .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    public void deletedArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article article = Article.builder()
            .title(title)
            .content(content)
            .build();

        Article save = blogRepository.save(article);

        // when
        mockMvc.perform(delete(url, save.getId()))
            .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(0);
    }

    @Test
    public void updateArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article article = Article.builder()
            .title(title)
            .content(content)
            .build();

        Article save = blogRepository.save(article);

        final String newTitle = "title1";
        final String newContent = "content2";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, save.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article updateArticle = blogRepository.findById(save.getId()).get();

        assertThat(updateArticle.getTitle()).isEqualTo(newTitle);
        assertThat(updateArticle.getContent()).isEqualTo(newContent);
    }


}