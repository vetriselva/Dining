package com.vgoups.dining.mapper;

import com.vgoups.dining.dto.user.CreateUserRequest;
import com.vgoups.dining.dto.user.UpdateUserRequest;
import com.vgoups.dining.dto.user.UserResponse;
import com.vgoups.dining.entity.Role;
import com.vgoups.dining.entity.User;
import com.vgoups.dining.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setStatus(createUserRequest.getStatus());
        return user;
    }


    public UserResponse toResponse(User user) {
        UserResponse createUserRequest = new UserResponse();
        createUserRequest.setId(user.getId());
        createUserRequest.setName(user.getName());
        createUserRequest.setEmail(user.getEmail());
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getRoleName());
        }
        createUserRequest.setRoles(roles);
        return createUserRequest;
    }

    public User toUpdateEntity(UpdateUserRequest updateUserRequest) {
        User user = new User();
        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        user.setStatus(updateUserRequest.getStatus());
        return user;
    }

}
