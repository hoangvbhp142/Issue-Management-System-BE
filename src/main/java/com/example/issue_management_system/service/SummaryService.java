package com.example.issue_management_system.service;

import com.example.issue_management_system.dto.response.IssuePrioritySummaryDto;
import com.example.issue_management_system.dto.response.IssueStatusSummaryDto;
import com.example.issue_management_system.dto.response.IssueTypeSummaryDto;

public interface SummaryService {
    IssueStatusSummaryDto getIssueStatusSummaryDto(Integer projectId);
    IssueTypeSummaryDto getIssueTypeSummaryDto(Integer projectId);
    IssuePrioritySummaryDto getIssuePrioritySummaryDto(Integer projectId);
}
