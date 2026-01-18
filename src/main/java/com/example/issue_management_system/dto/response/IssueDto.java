package com.example.issue_management_system.dto.response;

import com.example.issue_management_system.entity.enums.IssuePriority;
import com.example.issue_management_system.entity.enums.IssueStatus;
import com.example.issue_management_system.entity.enums.IssueType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueDto implements Serializable {
    Integer id;
    Integer projectId;
    String title;
    String description;
    IssueStatus status;
    IssuePriority priority;
    IssueType type;
    LocalDateTime dueDate;
    LocalDateTime resolvedAt;
    UserDto reporter;
    UserDto assignee;
}
