package com.example.issue_management_system.service;

import com.example.issue_management_system.entity.enums.IssueStatus;
import com.example.issue_management_system.entity.Issue;

public interface IssueService {
    Issue assignIssue(Integer issueId, Integer assigneeId);
    Issue changeStatus(Integer issueId, IssueStatus newStatus, Integer userId);
}
