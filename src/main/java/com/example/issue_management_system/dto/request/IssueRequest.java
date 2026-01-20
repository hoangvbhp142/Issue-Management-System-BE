package com.example.issue_management_system.dto.request;

import com.example.issue_management_system.entity.enums.IssuePriority;
import com.example.issue_management_system.entity.enums.IssueStatus;
import com.example.issue_management_system.entity.enums.IssueType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {
    String title;
    String description;
    IssueStatus status;
    IssuePriority priority;
    IssueType type;
    LocalDateTime dueDate;
    Integer projectId;
}
