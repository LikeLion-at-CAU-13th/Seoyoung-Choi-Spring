package com.example.likelion13th_spring.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 이 클래스가 HTTP API 응답을 반환하는 컨트롤러임을 표시하는 역할.
public class HealthCheckController {

    @GetMapping("/health")  // 클라이언트가 /health로 GET 요청을 보내면 실행함.
    public String healthCheck(){
        return "OK";
    }
}
