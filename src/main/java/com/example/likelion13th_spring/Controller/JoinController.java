package com.example.likelion13th_spring.Controller;

import com.example.likelion13th_spring.dto.request.JoinRequestDto;
import com.example.likelion13th_spring.exception.MemberAlreadyExistsException;
import com.example.likelion13th_spring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinRequestDto joinRequestDto){
        try {
            memberService.join(joinRequestDto);
            return ResponseEntity.ok("회원가입 완료");
        } catch (MemberAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}

