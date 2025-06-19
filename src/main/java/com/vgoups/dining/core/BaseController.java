package com.vgoups.dining.core;

import com.vgoups.dining.util.pagination.ApiPaginationResponse;
import com.vgoups.dining.util.pagination.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected  <T> ApiResponse<T> response(Boolean status, String message, T data) {
        return ApiResponse.<T>builder()
                .status(true)
                .message(message)
                .data(data)
                .build();
    }

    protected  <T> ResponseEntity<ApiPaginationResponse<T>> simplePagination(Boolean status, String message, T data, String nextPageUrl, HttpStatus httpStatus) {
        return ResponseEntity.ok(ApiPaginationResponse.<T>builder()
                .status(true)
                .statusCode(httpStatus.value())
                .message(message)
                .nextUrl(nextPageUrl)
                .data(data)
                .build());
    }


}
