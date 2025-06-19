package com.vgoups.dining.util.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiPaginationResponse <T> {
    private Boolean status;
    private String message;
    private T data;
    private String nextUrl;
    private int statusCode;
}
