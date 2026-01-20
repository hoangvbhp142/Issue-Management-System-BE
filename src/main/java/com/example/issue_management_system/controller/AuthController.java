package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.dto.request.LoginRequest;
import com.example.issue_management_system.dto.request.UserRequest;
import com.example.issue_management_system.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/signin")
    public ApiResponse<?> login(@RequestBody LoginRequest request) {
        var response = authService.signIn(request);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @PostMapping("/signup")
    public ApiResponse<?> register(@RequestBody UserRequest request) {
        authService.signUp(request);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success"
        );
    }
}
