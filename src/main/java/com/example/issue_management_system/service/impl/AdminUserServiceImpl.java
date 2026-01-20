package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.request.AdminUserRequest;
import com.example.issue_management_system.dto.response.AdminUserDto;
import com.example.issue_management_system.entity.Role;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.exception.AlreadyExistException;
import com.example.issue_management_system.mapper.AdminUserMapper;
import com.example.issue_management_system.repository.UserRepository;
import com.example.issue_management_system.service.AdminUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminUserServiceImpl extends BaseServiceImpl<User, Integer, AdminUserRequest, AdminUserDto> implements AdminUserService {

    private final UserRepository userRepository;
    private final AdminUserMapper adminUserMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleServiceImpl roleService;

    public AdminUserServiceImpl(UserRepository userRepository,
                                AdminUserMapper adminUserMapper,
                                PasswordEncoder passwordEncoder,
                                RoleServiceImpl roleService) {
        super(userRepository, adminUserMapper);
        this.userRepository = userRepository;
        this.adminUserMapper = adminUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public AdminUserDto create(AdminUserRequest adminUserRequest) {
        if (userRepository.existsByUsername(adminUserRequest.getUsername()) || userRepository.existsByEmail(adminUserRequest.getEmail())) {
            throw new AlreadyExistException("Username hoac email da ton tai");
        }
        return super.create(adminUserRequest);
    }

    @Override
    public User onCreate(AdminUserRequest adminUserRequest, User e) {
        e.setPassword(passwordEncoder.encode(adminUserRequest.getPassword()));
        Role role = roleService.findByName("ROLE_USER");
        e.setRoles(Set.of(role));
        return super.onCreate(adminUserRequest, e);
    }
}
