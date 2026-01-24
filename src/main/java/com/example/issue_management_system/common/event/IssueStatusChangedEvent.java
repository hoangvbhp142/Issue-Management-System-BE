package com.example.issue_management_system.common.event;

public record IssueStatusChangedEvent(
        Integer issueId,
        Integer changedBy
) {
}
