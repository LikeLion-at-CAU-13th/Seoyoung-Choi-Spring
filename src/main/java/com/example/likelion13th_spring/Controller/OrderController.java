package com.example.likelion13th_spring.Controller;

import com.example.likelion13th_spring.dto.request.OrderRequestDto;
import com.example.likelion13th_spring.dto.request.OrderUpdateRequestDto;
import com.example.likelion13th_spring.dto.request.OrderDeleteRequestDto;
import com.example.likelion13th_spring.dto.response.OrderResponseDto;
import com.example.likelion13th_spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    // 주문 조회 of member
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(@RequestBody Long memberId) {
        return ResponseEntity.ok(orderService.getAllOrders(memberId));
    }

    // 단일 주문 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // 주문 수정
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderUpdateRequestDto dto) {
        return ResponseEntity.ok(orderService.updateOrderById(orderId, dto));
    }

    // 주문 삭제
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId, @RequestBody OrderDeleteRequestDto dto) {
        orderService.deleteOrder(orderId, dto);
        return ResponseEntity.ok("Order deleted");
    }
}
