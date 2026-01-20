package com.example.issue_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDto {
    Integer id;
    String username;
    String email;
    String fullName;
    List<RoleDto> roles;
}
