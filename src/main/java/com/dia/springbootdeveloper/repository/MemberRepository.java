package com.dia.springbootdeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dia.springbootdeveloper.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
