package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.*;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);

    // week 18 과제
    List<Member> findByNameStartingWith(String prefix);
    Page<Member> findByAgeGreaterThanEqualOrderByNameAsc(int age, Pageable pageable);
} //Spring Data JPA 가 자동으로 인식 + 내부적으로 JPQL 쿼리 생성해줌.
