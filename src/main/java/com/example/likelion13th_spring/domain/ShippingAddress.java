package com.example.likelion13th_spring.domain;

import com.example.likelion13th_spring.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 수령인, 전화번호, 도로명주소, 상세주소, 우편번호
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String addressDetail;
    @Column(nullable = false)
    private Long postcode;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Member buyer;
    //private String buyerName = buyer.getName();

    @OneToMany(mappedBy = "shippingAddress")
    private List<Orders> orders = new ArrayList<>();

    @Builder
    public ShippingAddress(String phoneNumber, String address, String addressDetail, Long postcode, Member buyer) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressDetail = addressDetail;
        this.postcode = postcode;
        this.buyer = buyer;
    }
}
