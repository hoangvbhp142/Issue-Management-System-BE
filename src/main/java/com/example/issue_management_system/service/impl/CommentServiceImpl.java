package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.CommentDto;
import com.example.issue_management_system.entity.Comment;
import com.example.issue_management_system.mapper.CommentMapper;
import com.example.issue_management_system.repository.CommentRepository;
import com.example.issue_management_system.request.CommentRequest;
import com.example.issue_management_system.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, Integer, CommentRequest, CommentDto>
        implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        super(commentRepository, commentMapper);
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> findAllByIssueId(Integer issueId) {
        return commentRepository.findAllByIssueId(issueId);
    }
}
