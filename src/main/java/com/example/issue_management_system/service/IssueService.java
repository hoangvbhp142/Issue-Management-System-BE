package com.example.issue_management_system.service;

import com.example.issue_management_system.dto.response.IssueDto;
import com.example.issue_management_system.entity.enums.IssueStatus;
import com.example.issue_management_system.entity.Issue;

import java.util.List;

public interface IssueService {
    IssueDto assignIssue(Integer issueId, Integer assigneeId);
    IssueDto changeStatus(Integer issueId, IssueStatus newStatus, Integer userId);
    List<IssueDto> findAllByProjectId(Integer projectId);
}
