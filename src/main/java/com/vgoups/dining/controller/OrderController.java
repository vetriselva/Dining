package com.vgoups.dining.controller;

import com.vgoups.dining.core.BaseController;
import com.vgoups.dining.dto.item.ItemResponse;
import com.vgoups.dining.dto.order.CreateOrderRequest;
import com.vgoups.dining.dto.order.OrderResponse;
import com.vgoups.dining.entity.Item;
import com.vgoups.dining.service.ItemService;
import com.vgoups.dining.service.OrderService;
import com.vgoups.dining.util.pagination.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/order")
@RestController
@RequiredArgsConstructor
public class OrderController extends BaseController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<OrderResponse>> create(@Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = orderService.save(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response(false,"Order created successfully", response));
    }
}
