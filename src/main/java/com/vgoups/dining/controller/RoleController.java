package com.vgoups.dining.controller;

import com.vgoups.dining.core.BaseController;
import com.vgoups.dining.dto.item.CreateItemRequest;
import com.vgoups.dining.dto.item.ItemResponse;
import com.vgoups.dining.dto.item.UpdateItemRequest;
import com.vgoups.dining.dto.role.CreateRoleRequest;
import com.vgoups.dining.dto.role.RoleResponse;
import com.vgoups.dining.dto.role.UpdateRoleRequest;
import com.vgoups.dining.dto.user.UserResponse;
import com.vgoups.dining.entity.Item;
import com.vgoups.dining.entity.Role;
import com.vgoups.dining.mapper.ItemMapper;
import com.vgoups.dining.mapper.RoleMapper;
import com.vgoups.dining.service.RoleService;
import com.vgoups.dining.util.pagination.ApiPaginationResponse;
import com.vgoups.dining.util.pagination.ApiResponse;
import com.vgoups.dining.util.pagination.PaginationConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController extends BaseController {

    private final RoleService roleService;

    @PostMapping("/list-by-filter")
    public ResponseEntity<ApiPaginationResponse<List<RoleResponse>>> listByFilter(
            @RequestParam Map<String, String> filters,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE + "") int page,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE + "") int size,
            HttpServletRequest httpServletRequest
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Role> roles = roleService.findByCriteria(filters, pageable);
        String nextUrl = null;

        if(roles.hasNext()){
            String baseUrl = httpServletRequest.getRequestURL().toString();
            nextUrl = baseUrl +"?page="+ (page +1) +"size="+size;
        }

        List<RoleResponse> result = roles
                .map(RoleMapper::toResponse).toList();

        return simplePagination(true,"Role list", result, nextUrl, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<RoleResponse>> create(@Valid @RequestBody CreateRoleRequest request) {
        RoleResponse response = RoleMapper.toResponse(roleService.save(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response(false,"Created successfully", response));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse<RoleResponse>> update(
            @PathVariable Long id,
            @RequestBody UpdateRoleRequest request
    ) {
        if (roleService.existsByNameAndIdNot(request.getRoleName(), id)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response(false,"Name already exists", null));

        }
        Role role = roleService.findById(id);
        if(role == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false,"Item not found", null));
        }
        RoleResponse updated = roleService.update(role, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true,"Role updated successfully", updated));

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Role role = roleService.findById(id);
        if(role == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false,"Item not found", null));
        }
        roleService.delete(role);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true,"Role updated successfully", null));

    }


    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<RoleResponse>> activate(@PathVariable Long id) {
        Role role = roleService.findById(id);
        if(role == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false,"Item not found", null));
        }
       RoleResponse response = roleService.activateOrInactive(role, Boolean.TRUE);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true,"Role updated successfully", response));

    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<RoleResponse>> deActivate(@PathVariable Long id) {
        Role role = roleService.findById(id);
        if(role == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response(false,"Item not found", null));
        }
        RoleResponse response = roleService.activateOrInactive(role, Boolean.FALSE);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response(true,"Role updated successfully", response));

    }
}

