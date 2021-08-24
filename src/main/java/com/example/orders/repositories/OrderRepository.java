package com.example.orders.repositories;

import com.example.orders.entities.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Costumer, Long> {
    Costumer findOrderByOrderId(Long orderId);
}
