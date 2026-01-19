package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.request.RoleRequest;
import com.example.issue_management_system.dto.response.RoleDto;
import com.example.issue_management_system.entity.Role;
import com.example.issue_management_system.exception.NotFoundException;
import com.example.issue_management_system.mapper.RoleMapper;
import com.example.issue_management_system.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer, RoleRequest, RoleDto> {

    private final RoleRepository repository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository repository, RoleMapper roleMapper) {
        super(repository, roleMapper);
        this.repository = repository;
        this.roleMapper = roleMapper;
    }

    public Role findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Khong tim thay role"));
    }
}
