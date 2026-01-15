package com.example.issue_management_system.dto;

import com.example.issue_management_system.common.enums.IssueStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class IssueDto implements Serializable {
    Integer id;
    String title;
    String description;
    IssueStatus status;
    UserDto reporter;
    UserDto assignee;
}
