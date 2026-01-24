package com.example.issue_management_system.service;

import com.example.issue_management_system.common.event.IssueAssignedEvent;
import com.example.issue_management_system.common.event.IssueStatusChangedEvent;

public interface NotificationService {
    void createIssueAssigned(IssueAssignedEvent event);
    void changeIssueStatus(IssueStatusChangedEvent event);
}
