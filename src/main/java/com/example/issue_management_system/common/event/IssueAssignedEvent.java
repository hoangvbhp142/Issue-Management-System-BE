package com.example.issue_management_system.common.event;

public record IssueAssignedEvent(
        Integer issueId,
        Integer assigneeId,
        Integer assignedBy
) {
}
