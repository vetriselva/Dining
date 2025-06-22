package com.vgoups.dining.mapper;

import com.vgoups.dining.dto.user.CreateUserRequest;
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
    private final RoleRepository roleRepository;

    public User toEntity(CreateUserRequest createUserRequest) throws BadRequestException {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(createUserRequest.getRolesId()));
        if (roles.size() != createUserRequest.getRolesId().size()) {
            throw new BadRequestException("One or more roles not found");
        }
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setStatus(createUserRequest.getStatus());
        user.setRoles(roles);
        return user;
    }


    public UserResponse entityToResponse(User user) {
        UserResponse createUserRequest = new UserResponse();
        createUserRequest.setName(user.getName());
        createUserRequest.setEmail(user.getEmail());
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getRoleName());
        }
        createUserRequest.setRoles(roles);
        return createUserRequest;
    }

}
