package com.dia.springbootdeveloper.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dia.springbootdeveloper.dto.request.AddArticleRequest;
import com.dia.springbootdeveloper.dto.request.UpdateArticleRequest;
import com.dia.springbootdeveloper.entity.Article;
import com.dia.springbootdeveloper.entity.User;
import com.dia.springbootdeveloper.repository.BlogRepository;
import com.dia.springbootdeveloper.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
        blogRepository.deleteAll();
    }

    @BeforeEach
    void setSecurityContext() {
        userRepository.deleteAll();
        user = userRepository.save(User.builder()
            .email("user@gamil.com")
            .password("test")
            .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
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

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when
        ResultActions result = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .principal(principal)
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
        Article savedArticle = createDefaultArticle();

        // when
        ResultActions result = mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON));

        // then
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()))
            .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()));
    }

    @DisplayName("findById")
    @Test
    public void findArticle() throws Exception {
        // given
        final String url = "/api/article/{id}";
        Article savedArticle = createDefaultArticle();

        // when
        final ResultActions result = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(savedArticle.getTitle()))
            .andExpect(jsonPath("$.content").value(savedArticle.getContent()));
    }

    @Test
    public void deletedArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
            .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(0);
    }

    @Test
    public void updateArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();


        final String newTitle = "title1";
        final String newContent = "content2";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article updateArticle = blogRepository.findById(savedArticle.getId()).get();

        assertThat(updateArticle.getTitle()).isEqualTo(newTitle);
        assertThat(updateArticle.getContent()).isEqualTo(newContent);
    }

    private Article createDefaultArticle() {
        return blogRepository.save(Article.builder()
            .title("title")
            .author(user.getUsername())
            .content("content")
            .build());
    }


}