package com.example.issue_management_system.request;

import com.example.issue_management_system.common.enums.IssueStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class IssueHistoryRequest implements Serializable {
    Integer issueId;
    IssueStatus oldStatus;
    IssueStatus newStatus;
    Integer changedBy;
}
