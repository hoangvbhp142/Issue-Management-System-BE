package com.example.issue_management_system.service;

import com.example.issue_management_system.dto.request.ChangePasswordRequest;
import com.example.issue_management_system.dto.request.UpdateProfileRequest;
import com.example.issue_management_system.dto.response.UserDto;
import com.example.issue_management_system.entity.User;

public interface UserService {
    User getUserAuthentication();
    UserDto getCurrentUser();
    UserDto updateProfile(UpdateProfileRequest request);
    void changePassword(ChangePasswordRequest request);
}
