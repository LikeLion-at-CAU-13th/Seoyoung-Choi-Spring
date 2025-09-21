package com.example.likelion13th_spring.domain;

import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.domain.ShippingAddress;
import com.example.likelion13th_spring.enums.DeliverStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliverStatus deliverStatus; // 배송상태

    @ManyToOne
    @JoinColumn(name ="buyer_id")
    private Member buyer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrders> productOrders = new ArrayList<>();

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;

    public void update(String phoneNumber, String address, String addressDetail, Long postcode){
        this.shippingAddress.setPhoneNumber((phoneNumber));
        this.shippingAddress.setAddress(address);
        this.shippingAddress.setAddressDetail(addressDetail);
        this.shippingAddress.setPostcode(postcode);
    }
}
