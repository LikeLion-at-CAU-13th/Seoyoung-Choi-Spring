package com.example.likelion13th_spring.service;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.dto.request.JoinRequestDto;
import com.example.likelion13th_spring.repository.MemberRepository;
import com.example.likelion13th_spring.exception.MemberAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Page<Member> getMembersByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return memberRepository.findAll(pageable);
    }

    // 비밀번호 인코더 DI(생성자 주입)
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(JoinRequestDto joinRequestDto){
        // 해당 name이 이미 존재하는 경우
        if (memberRepository.existsByName(joinRequestDto.getName())){
            throw new MemberAlreadyExistsException("이미 존재하는 사용자 이름입니다.");
        }

        // 유저 객체 생성
        Member member = joinRequestDto.toEntity(bCryptPasswordEncoder);

        // 유저 정보 저장
        memberRepository.save(member);
    }
}
