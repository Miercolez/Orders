package com.example.orders.controllers;

import com.example.orders.entities.Costumer;
import com.example.orders.massagingRabbitMQ.RabbitMQConfiguration;
import com.example.orders.repositories.OrderRepository;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final String uri = "http://localhost:8080/pizzas/";
    private final RabbitTemplate rabbitTemplate;


    public OrderController(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the pizza");
            }
        }

        costumer.setTotalPrice(price);
        costumer.setPizzaNames(result);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName,
                "foo.bar.baz", new Gson().toJson(costumer));

        return new ResponseEntity<>(orderRepository.save(costumer), HttpStatus.CREATED);
    }

}
