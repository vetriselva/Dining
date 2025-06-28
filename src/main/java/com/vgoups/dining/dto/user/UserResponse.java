package com.vgoups.dining.dto.user;

import com.vgoups.dining.entity.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String status;
    private List<String> roles;
}
