package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.dto.request.RoleRequest;
import com.example.issue_management_system.service.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController extends BaseController<Integer, RoleRequest>{

    private final RoleServiceImpl roleService;

    @GetMapping("/{id}")
    @Override
    public ApiResponse<?> getById(Integer id) {
        var response = roleService.findByIdAndIsDeletedFalse(id);
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
        var response = roleService.findAllByIsDeletedFalse(pageable);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @PostMapping("")
    @Override
    public ApiResponse<?> create(RoleRequest request) {
        var response = roleService.create(request);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Success",
                response
        );
    }

    @PutMapping("/{id}")
    @Override
    public ApiResponse<?> update(Integer id, RoleRequest request) {
        var response = roleService.update(id, request);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @DeleteMapping("/{id}")
    @Override
    public ApiResponse<?> delete(Integer id) {
        roleService.delete(id);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success"
        );
    }
}
