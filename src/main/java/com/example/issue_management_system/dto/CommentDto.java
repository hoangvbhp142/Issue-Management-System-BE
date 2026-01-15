package com.example.issue_management_system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto implements Serializable {
    Integer id;
    String content;
    IssueDto issue;
    UserDto user;
}
