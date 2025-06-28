package com.vgoups.dining.mapper;

import com.vgoups.dining.dto.item.CreateItemRequest;
import com.vgoups.dining.dto.item.ItemResponse;
import com.vgoups.dining.dto.item.UpdateItemRequest;
import com.vgoups.dining.dto.role.CreateRoleRequest;
import com.vgoups.dining.dto.role.RoleResponse;
import com.vgoups.dining.dto.role.UpdateRoleRequest;
import com.vgoups.dining.entity.Item;
import com.vgoups.dining.entity.Role;

public class RoleMapper {
    public static Role toEntity(CreateRoleRequest request) {
        Role role = new Role();
        role.setRoleName(request.getRoleName());
        role.setStatus(request.getStatus());
        return role;
    }

    public static RoleResponse toResponse(Role entity) {
        RoleResponse request = new RoleResponse();
        request.setRoleId(entity.getRoleId());
        request.setRoleName(entity.getRoleName());
        request.setStatus(entity.getStatus());
        return request;
    }

    public static Role updateEntity(UpdateRoleRequest request, Role entity) {
        entity.setRoleName(request.getRoleName());
        entity.setStatus(request.getStatus());
        return entity;
    }
}
