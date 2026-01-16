package com.example.issue_management_system.service;

import com.example.issue_management_system.dto.CommentDto;
import com.example.issue_management_system.entity.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAllByIssueId(Integer issueId);
}
