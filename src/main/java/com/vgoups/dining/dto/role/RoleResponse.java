package com.vgoups.dining.dto.role;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleResponse {
    private Long roleId;
    private String roleName;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
