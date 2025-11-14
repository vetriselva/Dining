package com.vgoups.dining.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class CreateOrderRequest {
    @NotBlank(message = "Requested by field is required")
    private Long requestedBy;

    @NotNull
    private Set<@NotNull(message = "Item ID must not be null") Long> itemId;

}
