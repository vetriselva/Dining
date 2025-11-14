package com.vgoups.dining.dto.diningTable;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiningResponse {
    private Long diningId;
    private String name;
    private Integer memberCount;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
