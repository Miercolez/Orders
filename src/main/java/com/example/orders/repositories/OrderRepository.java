package com.example.orders.repositories;

import com.example.orders.entities.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Costumer, Long> {
}
