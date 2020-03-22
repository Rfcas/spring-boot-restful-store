package com.example.store.impl.controllers;

import com.example.store.impl.logic.services.OrderService;
import com.example.store.model.Order;
import com.example.store.model.requests.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(@RequestBody OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody @Valid CreateOrderRequest product) {
        return orderService.create(product);
    }

    @GetMapping(value = "/list")
    public List<Order> listOrders() {
        return orderService.listOrders();
    }

}
