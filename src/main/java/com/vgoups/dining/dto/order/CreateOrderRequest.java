package com.vgoups.dining.dto.order;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    @NotNull(message = "Requested by field is required")
    private Long requestedBy;

    @NotNull
    private List<@NotNull(message = "Item ID must not be null") Long> itemId;
}
