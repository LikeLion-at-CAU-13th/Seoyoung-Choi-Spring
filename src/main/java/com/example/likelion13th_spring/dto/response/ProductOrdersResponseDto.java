package com.example.likelion13th_spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductOrdersResponseDto {

    private Long productId;      // 상품 ID
    private String productName;  // 상품 이름
    private Integer quantity;    // 주문 수량
}