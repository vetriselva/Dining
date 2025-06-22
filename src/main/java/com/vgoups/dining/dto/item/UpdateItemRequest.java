package com.vgoups.dining.dto.item;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateItemRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be at most 50 characters")
    private String name;

    @Size(max = 200, min = 0)
    private String description;

    @NotNull(message = "Status must be specified")
    private Boolean status;
}

