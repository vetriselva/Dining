package com.vgoups.dining.controller;

import com.vgoups.dining.core.BaseController;
import com.vgoups.dining.dto.diningTable.CreateDiningTableRequest;
import com.vgoups.dining.dto.user.CreateUserRequest;
import com.vgoups.dining.dto.user.UserResponse;
import com.vgoups.dining.entity.User;
import com.vgoups.dining.mapper.DiningTableMapper;
import com.vgoups.dining.mapper.UserMapper;
import com.vgoups.dining.repository.RoleRepository;
import com.vgoups.dining.repository.UserRepository;
import com.vgoups.dining.service.UserService;
import com.vgoups.dining.util.pagination.ApiPaginationResponse;
import com.vgoups.dining.util.pagination.ApiResponse;
import com.vgoups.dining.util.pagination.PaginationConstants;
import jakarta.servlet.http.HttpServletRequest;
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

    private final UserRepository userRepository;
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
        Page<User> result = userService.findByCriteria(filter, pageable);
        String nextUrl = null;
        if(result.hasNext()) {
            String baseUrl = request.getRequestURL().toString();
            nextUrl = baseUrl + "?page=" + (page + 1) + "&size="+size;
        }

        List<UserResponse> userList = result
                .map(userMapper::entityToResponse)
                .toList();

        return simplePagination(true,"User list", userList, nextUrl, HttpStatus.OK);
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


}
