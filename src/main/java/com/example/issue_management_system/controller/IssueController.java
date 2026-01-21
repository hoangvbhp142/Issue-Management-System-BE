package com.example.issue_management_system.controller;

import com.example.issue_management_system.entity.enums.IssueStatus;
import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.dto.request.IssueRequest;
import com.example.issue_management_system.service.impl.IssueServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/issues")
@RequiredArgsConstructor
public class IssueController extends BaseController<Integer, IssueRequest> {

    private final IssueServiceImpl issueService;

    @GetMapping("/{id}")
    @Override
    public ApiResponse<?> getById(Integer id) {
        var response = issueService.findByIdAndIsDeletedFalse(id);
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
        var response = issueService.findAllByIsDeletedFalse(pageable);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @PostMapping("")
    @Override
    public ApiResponse<?> create(IssueRequest request) {
        var response = issueService.create(request);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Success",
                response
        );
    }

    @PutMapping("/{id}")
    @Override
    public ApiResponse<?> update(Integer id, IssueRequest request) {
        var response = issueService.update(id, request);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @PutMapping("/{id}/assign")
    public ApiResponse<?> assign(
            @PathVariable Integer id,
            @RequestParam Integer assigneeId
    ) {
        var response = issueService.assignIssue(id, assigneeId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @PutMapping("/{id}/status")
    public ApiResponse<?> changeStatus(
            @PathVariable Integer id,
            @RequestParam IssueStatus status
    ) {
        var response = issueService.changeStatus(id, status);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @DeleteMapping("/{id}")
    @Override
    public ApiResponse<?> delete(Integer id) {
        issueService.delete(id);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success"
        );
    }

    @GetMapping("/{projectId}/project")
    public ApiResponse<?> getAllByProject(@PathVariable Integer projectId) {
        var response = issueService.findAllByProjectId(projectId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @GetMapping("/{projectId}/board")
    public ApiResponse<?> getBoardByProject(@PathVariable Integer projectId) {
        var response = issueService.getBoardByProjectId(projectId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }
}

