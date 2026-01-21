package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.service.impl.IssueHistoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/issue-histories")
@RequiredArgsConstructor
public class IssueHistoryController {

    private final IssueHistoryServiceImpl issueHistoryService;

    @GetMapping("/issue/{issueId}")
    public ApiResponse<?> getByIssue(
            @PathVariable Integer issueId,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNum
    ) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        var response = issueHistoryService.findAllByIssueId(issueId, pageable);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<?> getByProject(
            @PathVariable Integer projectId,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNum
    ) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        var response = issueHistoryService.findAllByProjectId(projectId, pageable);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }
}

