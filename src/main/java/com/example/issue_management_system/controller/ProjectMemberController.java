package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.dto.request.ProjectMemberRequest;
import com.example.issue_management_system.service.impl.ProjectMemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/projects/{projectId}/members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberServiceImpl projectMemberService;

    @PostMapping
    public ApiResponse<?> addMember(@PathVariable Integer projectId,
                                    @RequestBody ProjectMemberRequest request) {
        projectMemberService.addMember(
                projectId,
                request.getUserId()
        );

        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Add member successfully"
        );
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<?> removeMember(@PathVariable Integer projectId,
                                       @PathVariable Integer userId) {
        projectMemberService.removeMember(
                projectId,
                userId
        );

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Remove member successfully"
        );
    }

    @PutMapping("/{userId}/role")
    public ApiResponse<?> changeRole(@PathVariable Integer projectId,
                                     @PathVariable Integer userId,
                                     @RequestBody ProjectMemberRequest request) {
        projectMemberService.changeRole(
                projectId,
                userId,
                request.getRole()
        );

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Change role successfully"
        );
    }

    @GetMapping
    public ApiResponse<?> getMembers(@PathVariable Integer projectId) {
        var members = projectMemberService.getMembers(projectId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                members
        );
    }
}

