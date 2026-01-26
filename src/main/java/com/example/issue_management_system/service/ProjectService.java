package com.example.issue_management_system.service;

import com.example.issue_management_system.dto.response.ProjectDto;

import java.util.List;

public interface ProjectService {
    void archiveProject(Integer projectId, Integer currentUserId);
    List<ProjectDto> findProjectVisibleToUser();
}
