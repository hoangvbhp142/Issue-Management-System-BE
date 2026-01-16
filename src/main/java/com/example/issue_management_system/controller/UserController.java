package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.response.ApiResponse;
import com.example.issue_management_system.request.UserRequest;
import com.example.issue_management_system.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController extends BaseController<Integer, UserRequest> {

    private final UserServiceImpl userService;

    @GetMapping("/{id}")
    @Override
    public ApiResponse<?> getById(Integer id) {
        var response = userService.findAllByIsDeletedFalse(id);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @GetMapping("")
    @Override
    public ApiResponse<?> getAll(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        var response = userService.findAllByIsDeletedFalse(pageable);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @PostMapping("")
    @Override
    public ApiResponse<?> create(UserRequest request) {
        var response = userService.create(request);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Success",
                response
        );
    }

    @PutMapping("/{id}")
    @Override
    public ApiResponse<?> update(Integer id, UserRequest request) {
        var response = userService.update(id, request);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @DeleteMapping("/{id}")
    @Override
    public ApiResponse<?> delete(Integer id) {
        userService.delete(id);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success"
        );
    }
}
