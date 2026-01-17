package com.example.issue_management_system.request;

import com.example.issue_management_system.common.enums.ProjectRole;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectMemberRequest implements Serializable {
    ProjectRole role;
    Integer projectId;
    Integer userId;
}
