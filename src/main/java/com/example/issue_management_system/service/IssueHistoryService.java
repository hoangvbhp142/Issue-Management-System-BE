package com.example.issue_management_system.service;

import com.example.issue_management_system.dto.response.IssueHistoryDto;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.IssueHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueHistoryService {
    IssueHistoryDto create(IssueHistory issueHistory);
    Page<IssueHistoryDto> findAllByIssueId(Integer issueId, Pageable pageable);
    Page<IssueHistoryDto> findAllByProjectId(Integer projectId, Pageable pageable);
}
