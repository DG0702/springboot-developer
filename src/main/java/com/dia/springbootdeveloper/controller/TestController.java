package com.dia.springbootdeveloper.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.springbootdeveloper.entity.Member;
import com.dia.springbootdeveloper.service.TestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test")
    public List<Member> getAllMembers(){
        return testService.getAllMembers();
    }

}
