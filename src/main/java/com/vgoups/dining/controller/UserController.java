package com.vgoups.dining.controller;

import com.vgoups.dining.core.BaseController;
import com.vgoups.dining.dto.user.CreateUserRequest;
import com.vgoups.dining.dto.user.UpdateUserRequest;
import com.vgoups.dining.dto.user.UserResponse;
import com.vgoups.dining.entity.User;
import com.vgoups.dining.mapper.UserMapper;
import com.vgoups.dining.service.UserService;
import com.vgoups.dining.util.pagination.ApiPaginationResponse;
import com.vgoups.dining.util.pagination.ApiResponse;
import com.vgoups.dining.util.pagination.PaginationConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping("/list-by-filter")
    public ResponseEntity<ApiPaginationResponse<List<UserResponse>>> list(
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE + "") int page,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE + "") int size,
            @RequestBody Map<String, String> filter,
            HttpServletRequest request
            ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> result = userService.findByCriteria(filter, pageable);
        String nextUrl = null;
        if(result.hasNext()) {
            String baseUrl = request.getRequestURL().toString();
            nextUrl = baseUrl + "?page=" + (page + 1) + "&size="+size;
        }

        return simplePagination(true,"User list", result.getContent(), nextUrl, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid  @RequestBody CreateUserRequest request) throws BadRequestException {
        if(userService.existsUserByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(response(false, "Email Id already exists", null));
        }
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response(true, "User created successfully", response));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) throws BadRequestException {
        if(userService.existsUserByEmailNotId(request.getEmail(), id)){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response(false, "Email Id already exists", null));
        }
        UserResponse response = userService.updateById(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true,"User updated successfully", response));
    }

    public ResponseEntity<ApiResponse<UserResponse>> delete(@PathVariable Long id) {
        User user = userService.findById(id);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response(false, "User not found", null));
        }
        userService.delete(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true, "User deleted successfully", null));

    }

    @PutMapping("/{id}/active")
    public ResponseEntity<ApiResponse<UserResponse>> activate(@PathVariable Long id) {
        User user = userService.findById(id);
        if(user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false,"Item not found", null));
        }
        UserResponse response = userService.activateOrInactive(user, true);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true, "User activated successfully", response));

    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<UserResponse>> deActivate(@PathVariable Long id) {
        User user = userService.findById(id);
        if(user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false,"Item not found", null));
        }
        UserResponse response = userService.activateOrInactive(user, false);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true, "User deactivated successfully", response));

    }


}
