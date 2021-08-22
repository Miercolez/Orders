package com.example.orders.controllers;

import com.example.orders.entities.Costumer;
import com.example.orders.repositories.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final String uri = "http://localhost:8080/pizzas/";

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    List<Costumer> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping("/orders/{name}")
    ResponseEntity<Costumer> sendOrder(@RequestBody Costumer costumer, @PathVariable("name") String name) {

        String[] pizzas = name.split(",");

        List<String> result = new ArrayList<>();
        int price = 0;

        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i < pizzas.length; i++) {
            try{
                String[] res = restTemplate.getForObject(uri + pizzas[i], String.class).split("[:,\"]+");
                result.add("Name: " + res[4] + ", Price: " + res[6] + "kr");
                price += Integer.parseInt(res[6]);
            }catch (HttpClientErrorException e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        costumer.setTotalPrice(price);
        costumer.setPizzaNames(result);

        return new ResponseEntity<>(orderRepository.save(costumer), HttpStatus.CREATED);
    }
}
