package com.vgoups.dining.dto.orderItem;

import com.vgoups.dining.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderItemResponse {
    private Long orderItem;
    private Integer qty;
    private String status;
    private User preparedBy;
    private LocalDateTime createdAt;
}
