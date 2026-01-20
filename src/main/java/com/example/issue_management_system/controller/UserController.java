package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.dto.request.ChangePasswordRequest;
import com.example.issue_management_system.dto.request.UpdateProfileRequest;
import com.example.issue_management_system.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/me")
    public ApiResponse<?> getCurrentUser() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                userService.getCurrentUser()
        );
    }

    @PutMapping("/me/profile")
    public ApiResponse<?> updateProfile(@RequestBody UpdateProfileRequest request) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Profile updated successfully",
                userService.updateProfile(request)
        );
    }

    @PutMapping("/me/password")
    public ApiResponse<?> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Password changed successfully"
        );
    }
}

