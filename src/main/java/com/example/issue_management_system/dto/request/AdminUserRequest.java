package com.example.issue_management_system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserRequest implements Serializable {
    String username;
    String email;
    String password;
    String fullName;
    List<Integer> roleIds;
}
