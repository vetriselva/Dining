package com.vgoups.dining.service;


import com.vgoups.dining.dto.role.CreateRoleRequest;
import com.vgoups.dining.dto.role.RoleResponse;
import com.vgoups.dining.dto.role.UpdateRoleRequest;
import com.vgoups.dining.entity.Role;
import com.vgoups.dining.mapper.RoleMapper;
import com.vgoups.dining.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Page<Role> findByCriteria(Map<String, String> filters, Pageable pageable) {
        return roleRepository.findByCriteria(filters, pageable);
    }

    public Role save(CreateRoleRequest request) {
        return roleRepository.save(RoleMapper.toEntity(request));
    }

    public Boolean existsByNameAndIdNot(String name, Long id) {
        return roleRepository.existsByRoleNameAndRoleIdNot(name, id);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public RoleResponse update(Role role, UpdateRoleRequest request) {
        RoleMapper.updateEntity(request, role);
        return RoleMapper.toResponse(roleRepository.save(role));
    }

    public void delete(Role role) {
        role.setDeletedAt(LocalDateTime.now());
        RoleMapper.toResponse(roleRepository.save(role));
    }

    public RoleResponse activateOrInactive(Role role, Boolean activated) {
        role.setStatus(activated);
        return RoleMapper.toResponse(roleRepository.save(role));
    }
}
