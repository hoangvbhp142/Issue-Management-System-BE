package com.example.issue_management_system.service;

import com.example.issue_management_system.dto.response.IssueDto;
import com.example.issue_management_system.entity.enums.IssueStatus;

import java.util.List;
import java.util.Map;

public interface IssueService {
    IssueDto assignIssue(Integer issueId, Integer assigneeId);
    IssueDto changeStatus(Integer issueId, IssueStatus newStatus);
    List<IssueDto> findAllByProjectId(Integer projectId);
    Map<IssueStatus, List<IssueDto>> getBoardByProjectId(Integer projectId);
}
