package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.dto.request.LoginRequest;
import com.example.issue_management_system.dto.request.UserRequest;
import com.example.issue_management_system.dto.response.LoginDto;
import com.example.issue_management_system.security.jwt.JwtUtils;
import com.example.issue_management_system.security.service.CustomUserDetails;
import com.example.issue_management_system.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userService;

    @PostMapping("/signin")
    public ApiResponse<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateJWT(authentication);
        LoginDto response = new LoginDto();
        response.setId(Objects.requireNonNull(userDetail).getId());
        response.setAccessToken(jwtToken);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @PostMapping("/signup")
    public ApiResponse<?> register(@RequestBody UserRequest request) {
        var response = userService.create(request);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }
}
