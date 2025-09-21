package com.example.likelion13th_spring.dto.response;

import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.ShippingAddress;
import com.example.likelion13th_spring.enums.DeliverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrderCreateResponseDto {

    private Long orderId;
    private DeliverStatus deliverStatus;

    // 배송지 정보
    private String buyer;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Long postcode;


    // DTO 변환 메소드
    public static OrderResponseDto fromEntity(Orders order) {
        ShippingAddress shipping = order.getShippingAddress();


        return OrderResponseDto.builder()
                .orderId(order.getId())
                .deliverStatus(order.getDeliverStatus())
                .buyer(shipping != null ? shipping.getBuyer().getName() : null)
                .phoneNumber(shipping != null ? shipping.getPhoneNumber() : null)
                .address(shipping != null ? shipping.getAddress() : null)
                .addressDetail(shipping != null ? shipping.getAddressDetail() : null)
                .postcode(shipping != null ? shipping.getPostcode() : null)
                .build();
    }
}