package com.example.issue_management_system.service;

import com.example.issue_management_system.dto.request.LoginRequest;
import com.example.issue_management_system.dto.response.LoginDto;

public interface UserService {
    LoginDto login(LoginRequest request);
}
