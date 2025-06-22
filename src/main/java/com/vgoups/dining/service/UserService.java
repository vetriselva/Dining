package com.vgoups.dining.service;

import com.vgoups.dining.dto.user.CreateUserRequest;
import com.vgoups.dining.dto.user.UserResponse;
import com.vgoups.dining.entity.User;
import com.vgoups.dining.mapper.UserMapper;
import com.vgoups.dining.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(CreateUserRequest request) throws BadRequestException {
        User user = userRepository.save(userMapper.toEntity(request));
        return userMapper.entityToResponse(user);
    }

    public Boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public Page<User> findByCriteria(Map<String, String> filter, Pageable pageable) {
        return userRepository.findByCriteria(filter, pageable);
    }

}
