package com.example.issue_management_system.dto.response;

import com.example.issue_management_system.entity.enums.ProjectStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDto {
    Integer id;
    String code;
    String name;
    String description;
    int issues;
    int members;
    int completed;
    int progress;
    ProjectStatus status;
}
