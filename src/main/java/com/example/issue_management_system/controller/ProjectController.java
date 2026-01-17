package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.response.ApiResponse;
import com.example.issue_management_system.request.ProjectRequest;
import com.example.issue_management_system.service.impl.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/projects")
@RequiredArgsConstructor
public class ProjectController extends BaseController<Integer, ProjectRequest> {

    private final ProjectServiceImpl projectService;

    @GetMapping("/{id}")
    @Override
    public ApiResponse<?> getById(Integer id) {
        var response = projectService.findByIdAndIsDeletedFalse(id);
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
        var response = projectService.findAllByIsDeletedFalse(pageable);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @PostMapping("")
    @Override
    public ApiResponse<?> create(ProjectRequest request) {
        var response = projectService.create(request);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Success",
                response
        );
    }

    @PutMapping("/{id}")
    @Override
    public ApiResponse<?> update(Integer id, ProjectRequest request) {
        var response = projectService.update(id, request);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @DeleteMapping("/{id}")
    @Override
    public ApiResponse<?> delete(Integer id) {
        projectService.delete(id);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success"
        );
    }
}
