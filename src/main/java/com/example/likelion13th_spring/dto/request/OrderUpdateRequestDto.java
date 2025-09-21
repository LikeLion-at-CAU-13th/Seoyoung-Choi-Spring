package com.example.likelion13th_spring.dto.request;

import lombok.Getter;

@Getter
public class OrderUpdateRequestDto {
    private Long memberId;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Long postcode;
}
