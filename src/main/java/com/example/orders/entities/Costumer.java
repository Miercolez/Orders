package com.example.orders.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Costumer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String realName;
    private int orderQuantity;
    private int totalPrice;
    private String orderTime = new Timestamp(System.currentTimeMillis()).toString().substring(0,19);
    private Long orderId = (int)(Math.random() * 10000) + Long.parseLong(orderTime.replaceAll("[-: ]", ""));

    @ElementCollection
    private List<String> pizzaNames;

    public Costumer() {
    }

    public Costumer(String realName, List<String> pizzaNames) {
        this.realName = realName;
        this.pizzaNames = pizzaNames;
        this.orderQuantity = pizzaNames.size();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String userName) {
        this.realName = userName;
    }

    public List<String> getPizzaNames() {
        return pizzaNames;
    }

    public void setPizzaNames(List<String> pizzaNames) {
        this.pizzaNames = pizzaNames;
        this.orderQuantity = pizzaNames.size();
    }

    public String getOrderTime() {
        return orderTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getOrderId() {
        return orderId;
    }
}
