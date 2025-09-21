package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByBuyerId(Long memberId);
}
