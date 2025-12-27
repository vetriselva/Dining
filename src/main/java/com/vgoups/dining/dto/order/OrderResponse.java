package com.vgoups.dining.dto.order;

import com.vgoups.dining.dto.orderItem.OrderItemResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long orderId;
    private List<OrderItemResponse> orderItems;
    private OrderStatus status;
    private LocalDateTime placedAt;
    private LocalDateTime preparedAt;
    private LocalDateTime completedAt;
}
