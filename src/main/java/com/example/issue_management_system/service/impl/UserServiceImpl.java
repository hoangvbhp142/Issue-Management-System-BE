package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.response.UserDto;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.mapper.UserMapper;
import com.example.issue_management_system.repository.UserRepository;
import com.example.issue_management_system.dto.request.UserRequest;
import com.example.issue_management_system.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer, UserRequest, UserDto> implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
}
