package com.vgoups.dining.mapper;

import com.vgoups.dining.dto.order.CreateOrderRequest;
import com.vgoups.dining.dto.orderItem.OrderItemResponse;
import com.vgoups.dining.dto.orderItem.OrderItemStatus;
import com.vgoups.dining.entity.Item;
import com.vgoups.dining.entity.OrderItem;

public class OrderItemMapper {
    public static OrderItem toEntity(CreateOrderRequest createOrderRequest, Item item) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setStatus(OrderItemStatus.OPEN);
        orderItem.setActive(Boolean.TRUE);
        orderItem.setQty(1);
        return orderItem;
    }

    public static OrderItemResponse toResponse(OrderItem orderItem) {
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setOrderItem(orderItem.getId());
        orderItemResponse.setPreparedBy(orderItem.getUser());
        orderItemResponse.setStatus(orderItem.getStatus());
        orderItemResponse.setQty(orderItem.getQty());
        orderItemResponse.setCreatedAt(orderItem.getCreatedAt());
        return orderItemResponse;
    }
}
