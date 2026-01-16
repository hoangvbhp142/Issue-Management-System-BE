package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.response.ApiResponse;
import com.example.issue_management_system.request.CommentRequest;
import com.example.issue_management_system.service.impl.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/comments")
@RequiredArgsConstructor
public class CommentController extends BaseController<Integer, CommentRequest> {

    private final CommentServiceImpl commentService;

    @Override
    public ApiResponse<?> getById(Integer integer) {
        return null;
    }

    @Override
    public ApiResponse<?> getAll(int pageSize, int pageNum) {
        return null;
    }

    @PostMapping("")
    @Override
    public ApiResponse<?> create(CommentRequest request) {
        var response = commentService.create(request);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Success",
                response
        );
    }

    @Override
    public ApiResponse<?> update(Integer integer, CommentRequest request) {
        return null;
    }

    @Override
    public ApiResponse<?> delete(Integer integer) {
        return null;
    }

    @GetMapping("/issue/{issueId}")
    public ApiResponse<?> getByIssue(@PathVariable Integer issueId) {
        var response = commentService.findAllByIssueId(issueId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }
}

