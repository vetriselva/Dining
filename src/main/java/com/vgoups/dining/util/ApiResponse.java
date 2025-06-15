package com.vgoups.dining.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse <T> {
    private Boolean status;
    private String message;
    private T data;
    private String nextUrl;
    private int statusCode;
}
