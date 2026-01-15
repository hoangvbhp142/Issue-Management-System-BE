package com.example.issue_management_system.request;

import com.example.issue_management_system.common.enums.IssueStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueRequest {
    String title;
    String description;
    IssueStatus status;
    Integer reporterId;
}
