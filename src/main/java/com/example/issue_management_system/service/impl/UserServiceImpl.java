package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.request.ChangePasswordRequest;
import com.example.issue_management_system.dto.request.UpdateProfileRequest;
import com.example.issue_management_system.dto.response.UserDto;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.exception.BusinessException;
import com.example.issue_management_system.exception.NotFoundException;
import com.example.issue_management_system.mapper.UserMapper;
import com.example.issue_management_system.repository.UserRepository;
import com.example.issue_management_system.dto.request.UserRequest;
import com.example.issue_management_system.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer, UserRequest, UserDto> implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = Objects.requireNonNull(authentication).getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public UserDto getCurrentUser() {
        return userMapper.toResponse(getUserAuthentication());
    }

    @Override
    public UserDto updateProfile(UpdateProfileRequest request) {
        User user = getUserAuthentication();
        user.setFullName(request.getFullName());
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = getUserAuthentication();
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BusinessException("Mat khau cu khong dung");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
