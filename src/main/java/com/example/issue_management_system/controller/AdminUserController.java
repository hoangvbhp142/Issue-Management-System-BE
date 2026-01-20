package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.dto.request.AdminUserRequest;
import com.example.issue_management_system.service.impl.AdminUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/admin/users")
@RequiredArgsConstructor
public class AdminUserController extends BaseController<Integer, AdminUserRequest> {

    private final AdminUserServiceImpl adminUserService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ApiResponse<?> getById(Integer id) {
        var response = adminUserService.findByIdAndIsDeletedFalse(id);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ApiResponse<?> getAll(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        var response = adminUserService.findAllByIsDeletedFalse(pageable);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ApiResponse<?> create(AdminUserRequest request) {
        var response = adminUserService.create(request);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Success",
                response
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ApiResponse<?> update(Integer id, AdminUserRequest request) {
        var response = adminUserService.update(id, request);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ApiResponse<?> delete(Integer id) {
        adminUserService.delete(id);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success"
        );
    }
}
