package com.example.issue_management_system.dto;

import com.example.issue_management_system.common.enums.ProjectRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectMemberDto {
    UserDto user;
    ProjectRole role;
}
