package com.example.issue_management_system.dto.response;

import com.example.issue_management_system.entity.enums.IssueStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

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
    UserDto reporter;
    UserDto assignee;
}
