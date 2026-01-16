package com.example.issue_management_system.service;

import com.example.issue_management_system.entity.IssueHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueHistoryService {
    IssueHistory create(IssueHistory issueHistory);

    Page<IssueHistory> findAllByIssueId(Integer issueId, Pageable pageable);
}
