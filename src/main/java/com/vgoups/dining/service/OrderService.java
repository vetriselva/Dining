package com.vgoups.dining.service;

import com.vgoups.dining.dto.order.CreateOrderRequest;
import com.vgoups.dining.dto.order.OrderResponse;
import com.vgoups.dining.entity.Item;
import com.vgoups.dining.entity.Order;
import com.vgoups.dining.entity.OrderItem;
import com.vgoups.dining.mapper.OrderItemMapper;
import com.vgoups.dining.mapper.OrderMapper;
import com.vgoups.dining.repository.ItemRepository;
import com.vgoups.dining.repository.OrderRepository;
import com.vgoups.dining.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    public OrderResponse save(CreateOrderRequest request) {
        Order order = OrderMapper.toEntity(request);
        order.setUser(userRepository.findById(1L).orElseThrow());
        List<Item> items = itemRepository.findAllById(request.getItemId());
        for (Item item : items) {
            OrderItem orderItem = OrderItemMapper.toEntity(request, item);
            order.addOrderItem(orderItem);
        }
        return OrderMapper.toResponse(orderRepository.save(order));
    }
}
