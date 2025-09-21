package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrders, Long> {
}
