package com.example.issue_management_system.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserRequest implements Serializable {
    String username;
    String email;
}
