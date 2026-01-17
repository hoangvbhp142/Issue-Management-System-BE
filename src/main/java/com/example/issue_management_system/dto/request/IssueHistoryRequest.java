package com.example.issue_management_system.dto.request;

import com.example.issue_management_system.entity.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueHistoryRequest implements Serializable {
    Integer issueId;
    IssueStatus newStatus;
    Integer changedBy;
}
