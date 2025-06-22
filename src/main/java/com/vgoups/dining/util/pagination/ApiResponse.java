package com.vgoups.dining.util.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse <T> {
    private Boolean status;
    private String message;
    private Boolean hasValidationError = false;
    private T data = null;
}
