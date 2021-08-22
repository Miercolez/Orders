package com.example.orders.controllers;

import com.example.orders.entities.Costumer;
import com.example.orders.repositories.OrderRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @GetMapping("/orders")
    List<Costumer> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping("/orders/{name}")
    Costumer sendOrder(@RequestBody Costumer costumer, @PathVariable("name") String name) {

        String[] pizzas = name.split(",");

        List<String> result = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8080/pizzas/";

        for (int i = 0; i < pizzas.length; i++) {
            String[] res = restTemplate.getForObject(uri + pizzas[i], String.class).split("[:,\"]+");
            result.add("Name: " + res[4] + ", Price: " + res[6] + "kr");
        }

        costumer.setPizzaNames(result);

        return orderRepository.save(costumer);
    }
}
