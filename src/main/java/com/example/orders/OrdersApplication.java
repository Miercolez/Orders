package com.example.orders;

import com.example.orders.entities.Costumer;
import com.example.orders.repositories.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class OrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(OrderRepository orderRepository) {

        return args -> {
            if (orderRepository.count() == 0) {
                orderRepository.save(new Costumer("Jonas",new ArrayList<>()));
            }
        };
    }
}
