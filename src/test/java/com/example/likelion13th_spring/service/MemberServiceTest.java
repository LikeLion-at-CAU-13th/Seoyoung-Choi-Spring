package com.example.likelion13th_spring.service;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.enums.Role;
import com.example.likelion13th_spring.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ThreadLocalRandom;



@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll(); // 전에 있던거 삭제하기(?)

        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder() // 객체 만들 수 있음
                    .name("user" + i)
                    .email("user" + i + "@test.com")
                    .address("서울시 테스트동 " + i + "번지")
                    .phoneNumber("010-1234-56" + String.format("%02d", i))
                    .age(i)
                    .deposit(1000 * i)
                    .isAdmin(false)
                    .role(Role.BUYER)
                    .build();

            memberRepository.save(member); // 멤버 저장하기
        });
    }

    @Test
    void testGetMembersByPage() {
        Page<Member> page = memberService.getMembersByPage(0, 10); // 10개씩 가져오기

        assertThat(page.getContent()).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(30); // 총 개수 30개인 지
        assertThat(page.getTotalPages()).isEqualTo(3); // 한페이지당 10개니까 총 3페이지가 맞는 지
        assertThat(page.getContent().get(0).getName()).isEqualTo("user1");
    }

    @Test
    void testGetMembersByNamePrefix(){
        // 멤버의 이름이 주어진 값으로 시작하는 경우에만 필터링하는 로직 작성
        String prefix = "user1";
        List<Member> result = memberRepository.findByNameStartingWith(prefix);

        assertThat(result).isNotEmpty();
        for(Member member : result){
            assertThat(member.getName()).startsWith(prefix);
        }
    }

    @Test
    void testGetAdultMembersSortedByName(){
        // 멤버 나이가 20 이상인 경우에만 조회, 이름 기준으로 오름차순 정렬 페이징 결과
        int minAge = 20;
        Pageable pageable = PageRequest.of(0, 10);

        Page<Member> pageResult = memberRepository.findByAgeGreaterThanEqualOrderByNameAsc(minAge, pageable);
        List<String> names = new ArrayList<>();

        assertThat(pageResult.getContent()).isNotEmpty();
        assertThat(pageResult.getContent().size()).isLessThanOrEqualTo(10);

        for (Member member : pageResult){
            assertThat(member.getAge()).isGreaterThanOrEqualTo(minAge);
            names.add(member.getName());
        }


        List<String> sortedNames = new ArrayList<>(names);
        Collections.sort(sortedNames);
        assertThat(names).isEqualTo(sortedNames); // 이름 오름차순 정렬 확인
    }
}