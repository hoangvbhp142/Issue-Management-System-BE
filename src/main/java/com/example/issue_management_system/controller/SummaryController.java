package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.service.impl.SummaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryServiceImpl summaryService;

    @GetMapping("/{projectId}/status")
    public ApiResponse<?> getIssueStatusSummaryByProjectId(@PathVariable Integer projectId) {
        var response = summaryService.getIssueStatusSummaryDto(projectId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @GetMapping("/{projectId}/type")
    public ApiResponse<?> getIssueTypeSummaryByProjectId(@PathVariable Integer projectId) {
        var response = summaryService.getIssueTypeSummaryDto(projectId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }

    @GetMapping("/{projectId}/priority")
    public ApiResponse<?> getIssuePrioritySummaryByProjectId(@PathVariable Integer projectId) {
        var response = summaryService.getIssuePrioritySummaryDto(projectId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                response
        );
    }
}
