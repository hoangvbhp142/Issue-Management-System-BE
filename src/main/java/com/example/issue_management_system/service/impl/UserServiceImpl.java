package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.request.LoginRequest;
import com.example.issue_management_system.dto.response.LoginDto;
import com.example.issue_management_system.dto.response.UserDto;
import com.example.issue_management_system.entity.Role;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.exception.AlreadyExistException;
import com.example.issue_management_system.mapper.UserMapper;
import com.example.issue_management_system.repository.UserRepository;
import com.example.issue_management_system.dto.request.UserRequest;
import com.example.issue_management_system.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer, UserRequest, UserDto> implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    private final RoleServiceImpl roleService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleServiceImpl roleService) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }

    @Override
    public LoginDto login(LoginRequest request) {
        return null;
    }

    @Override
    public UserDto create(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername()) || userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AlreadyExistException("Username hoac email da ton tai");
        }
        return super.create(userRequest);
    }

    @Override
    public User onCreate(UserRequest userRequest, User e) {
        e.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        Role role = roleService.findByName("ROLE_USER");
        e.setRoles(Set.of(role));
        return super.onCreate(userRequest, e);
    }
}
