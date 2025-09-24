package com.example.likelion13th_spring.service;

import com.example.likelion13th_spring.domain.*;
import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.dto.request.OrderDeleteRequestDto;
import com.example.likelion13th_spring.dto.response.OrderCreateResponseDto;
import com.example.likelion13th_spring.dto.request.OrderRequestDto;
import com.example.likelion13th_spring.dto.request.OrderUpdateRequestDto;
import com.example.likelion13th_spring.dto.response.OrderResponseDto;
import com.example.likelion13th_spring.enums.DeliverStatus;
import com.example.likelion13th_spring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member Not Found"));

        // 구매자 권한 확인
        if (!member.isBuyer()){
            throw new IllegalArgumentException("주문은 구매자만 등록할 수 있습니다.");
        }

        // 배송 정보 관련
        ShippingAddress shippingAddress;

        if (dto.getShippingAddressId() != null) {
            // 기존 배송지 조회
            shippingAddress = shippingAddressRepository
                    .findById(dto.getShippingAddressId())
                    .orElseThrow(() -> new IllegalArgumentException("배송지 없음"));
        } else {
            // 신규 배송지 생성
            shippingAddress = dto.toShippingAddressEntity(member);
            shippingAddressRepository.save(shippingAddress);
        }

        Orders orders = dto.toEntity(member, shippingAddress);
        Orders saved = orderRepository.save(orders);

        // 주문 상품 매핑
        for (OrderRequestDto.OrderProductDto p : dto.getProducts()) {
            Product product = productRepository.findById(p.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

            ProductOrders productOrders = ProductOrders.builder()
                    .orders(orders)
                    .product(product)
                    .quantity(p.getQuantity())
                    .build();

            productOrderRepository.save(productOrders);
            // 재고 차감
            product.reduceStock(p.getQuantity());
        }



        return OrderCreateResponseDto.fromEntity(saved);
    }

    // 특정 멤버의 모든 주문 조회
    public List<OrderResponseDto> getAllOrders(Long memberId) {
        // 구매자 조회
        Member buyer =  memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member Not Found"));

        // 주문 조회
        List<Orders> orders = orderRepository.findByBuyerId(memberId);

        return orders.stream()
                .map(OrderResponseDto::fromEntity) // 엔티티 → DTO 변환
                .toList();
    }

    // 특정 주문 조회
    public OrderResponseDto getOrderById(Long orderId) {
        // 주문 조회
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        return OrderResponseDto.fromEntity(order);
    }

    // 특정 주문 수정
    public OrderResponseDto updateOrderById(Long orderId, OrderUpdateRequestDto dto){
        // 판매자 조회
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 구매자가 존재하지 않습니다."));

        // 상품 조회
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        // 판매자 권한 확인
        if (!order.getBuyer().getId().equals(member.getId())) {
            throw new IllegalArgumentException("본인의 주문만 수정할 수 있습니다.");
        }

        // 상품 배송 상태 확인
        if (!(order.getDeliverStatus().equals(DeliverStatus.PREPARATION))){
            throw new IllegalArgumentException("이미 배송중 이거나 배송 완료된 상태입니다.");
        }

        // 수정
        order.update(dto.getPhoneNumber(),dto.getAddress(),dto.getAddressDetail(),dto.getPostcode());

        return OrderResponseDto.fromEntity(order);
    }

    // 특정 주문 삭제
    public void deleteOrder(Long orderId, OrderDeleteRequestDto dto) {
        // 판매자 조회
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 구매자가 존재하지 않습니다."));

        // 상품 조회
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        // 판매자 권한 확인
        if (!order.getBuyer().getId().equals(member.getId())) {
            throw new IllegalArgumentException("본인의 주문만 삭제할 수 있습니다.");
        }

        // 상품 배송 상태 확인
        if (!(order.getDeliverStatus().equals(DeliverStatus.COMPLETED))){
            throw new IllegalArgumentException("배송이 완료되지 않은 주문은 삭제할 수 없습니다.");
        }

        // 삭제
        orderRepository.delete(order);
    }
}
