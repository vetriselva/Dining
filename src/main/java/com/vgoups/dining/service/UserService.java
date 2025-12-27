package com.vgoups.dining.service;

import com.vgoups.dining.dto.user.CreateUserRequest;
import com.vgoups.dining.dto.user.UpdateUserRequest;
import com.vgoups.dining.dto.user.UserResponse;
import com.vgoups.dining.entity.Role;
import com.vgoups.dining.entity.User;
import com.vgoups.dining.mapper.UserMapper;
import com.vgoups.dining.repository.DiningTableRepository;
import com.vgoups.dining.repository.RoleRepository;
import com.vgoups.dining.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final DiningTableRepository diningTableRepository;

    public UserResponse createUser(CreateUserRequest request) throws BadRequestException {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRolesId()));
        if (roles.size() != request.getRolesId().size()) {
            throw new BadRequestException("One or more roles not found");
        }
        User user = userMapper.toEntity(request);
        user.setRoles(roles);
        return userMapper.toResponse(userRepository.save(user));
    }

    public Boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }


    @Transactional(readOnly = true)
    public Page<UserResponse> findByCriteria(
            Map<String, String> filter,
            Pageable pageable
    ) {
        Page<User> users = userRepository.findByCriteria(filter, pageable);

        return users.map(userMapper::toResponse);
    }

    public boolean existsUserByEmailNotId(String email, Long id) {
        return userRepository.existsUserByEmailAndIdNot(email, id);
    }

    public UserResponse updateById(Long id, UpdateUserRequest request) throws BadRequestException {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRolesId()));
        if (roles.size() != request.getRolesId().size()) {
            throw new BadRequestException("One or more roles not found");
        }
        User user = userMapper.toUpdateEntity(request);
        user.setRoles(roles);
        return userMapper.toResponse(userRepository.save(user));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(User user) {
        user.setDeletedAt(LocalDateTime.now());
        userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse activateOrInactive(User user, Boolean activated) {
        user.setStatus(activated);
        return userMapper.toResponse(userRepository.save(user));
    }
}
