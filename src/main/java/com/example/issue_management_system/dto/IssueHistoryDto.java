package com.example.issue_management_system.dto;

import com.example.issue_management_system.common.enums.IssueStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class IssueHistoryDto implements Serializable {
    Integer id;
    IssueDto issue;
    IssueStatus oldStatus;
    IssueStatus newStatus;
    UserDto changedBy;
}
