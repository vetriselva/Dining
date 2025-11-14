package com.vgoups.dining.controller;

import com.vgoups.dining.dto.order.CreateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/order")
@RestController
public class OrderController {
    @PostMapping("/create")
    public String create(@Valid @RequestBody CreateOrderRequest request) {
        return "success";
    }
}
