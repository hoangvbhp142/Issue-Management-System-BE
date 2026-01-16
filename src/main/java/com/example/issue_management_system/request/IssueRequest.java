package com.example.issue_management_system.request;

import com.example.issue_management_system.common.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {
    String title;
    String description;
    IssueStatus status;
    Integer reporterId;
}
