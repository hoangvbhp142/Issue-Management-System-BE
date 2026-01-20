package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.request.LoginRequest;
import com.example.issue_management_system.dto.request.UserRequest;
import com.example.issue_management_system.dto.response.LoginDto;
import com.example.issue_management_system.security.jwt.JwtUtils;
import com.example.issue_management_system.security.service.CustomUserDetails;
import com.example.issue_management_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final UserServiceImpl userService;

    @Override
    public LoginDto signIn(LoginRequest request) {
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
        return response;
    }

    @Override
    public void signUp(UserRequest request) {
        userService.create(request);
    }
}
