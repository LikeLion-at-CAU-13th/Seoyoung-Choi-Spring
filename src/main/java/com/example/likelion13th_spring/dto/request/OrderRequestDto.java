package com.example.likelion13th_spring.dto.request;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Product;
import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.ShippingAddress;
import com.example.likelion13th_spring.enums.DeliverStatus;
import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.dto.response.ProductResponseDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.Set;
import java.util.List;

@Getter
public class OrderRequestDto {
    private Long memberId;
    private DeliverStatus deliverStatus = DeliverStatus.PREPARATION;
    private String receiverName;

    // 신규 배송 정보 생성
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private String postcode;

    // 기존 배송 정보 선택
    private Long shippingAddressId;

    // 주문할 상품들
    private List<OrderProductDto> products;

    @Getter
    public static class OrderProductDto {
        private Long productId;
        private Integer quantity;
    }

    //@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private ShippingAddress shippingAddress;

    // DTO에서 신규 배송지 엔티티 생성
    public ShippingAddress toShippingAddressEntity(Member buyer) {
        if (shippingAddressId != null) {
            // 기존 배송지를 재사용할 때는 Service에서 처리
            return null;
        }
        return new ShippingAddress(
                this.phoneNumber,
                this.address,
                this.addressDetail,
                this.postcode,
                buyer
        );
    }

    public Orders toEntity(Member buyer, ShippingAddress shippingAddress) {
        return Orders.builder()
                .buyer(buyer)
                .deliverStatus(deliverStatus)
                .receiverName(receiverName)
                .shippingAddress(shippingAddress)
                .build();
    }
}
