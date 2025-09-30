package com.dia.springbootdeveloper.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.springbootdeveloper.entity.Member;
import com.dia.springbootdeveloper.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

    private final MemberRepository memberRepository;

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }
}
