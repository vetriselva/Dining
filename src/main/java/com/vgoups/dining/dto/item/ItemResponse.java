package com.vgoups.dining.dto.item;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemResponse {
    private Long itemId;
    private String name;
    private String description;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
