package com.vgoups.dining.service;

import com.vgoups.dining.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public boolean create() {

        return true;
    }
}
