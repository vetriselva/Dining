package com.vgoups.dining.mapper;

import com.vgoups.dining.dto.order.CreateOrderRequest;
import com.vgoups.dining.dto.order.OrderResponse;
import com.vgoups.dining.dto.orderItem.OrderItemResponse;
import com.vgoups.dining.entity.Order;
import com.vgoups.dining.entity.User;

import java.util.List;

public class OrderMapper {
    public static Order toEntity(CreateOrderRequest request) {
        Order order = new Order();
        return order;
    }

    public static OrderResponse toResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getId());
        orderResponse.setCompletedAt(order.getCompletedAt());
        orderResponse.setPlacedAt(order.getPlacedAt());
        orderResponse.setPreparedAt(order.getPreparedAt());

        List<OrderItemResponse> itemResponses =
                order.getOrderItems()
                        .stream()
                        .map(OrderItemMapper::toResponse)
                        .toList();

        orderResponse.setOrderItems(itemResponses);
        return orderResponse;
    }
}
