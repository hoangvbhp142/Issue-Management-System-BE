package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.CommentDto;
import com.example.issue_management_system.entity.Comment;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.mapper.CommentMapper;
import com.example.issue_management_system.repository.CommentRepository;
import com.example.issue_management_system.repository.IssueRepository;
import com.example.issue_management_system.repository.UserRepository;
import com.example.issue_management_system.request.CommentRequest;
import com.example.issue_management_system.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, Integer, CommentRequest, CommentDto>
        implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final UserRepository userRepository;
    private final IssueRepository issueRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentMapper commentMapper,
                              UserRepository userRepository,
                              IssueRepository issueRepository) {
        super(commentRepository, commentMapper);
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
    }

    @Override
    public List<CommentDto> findAllByIssueId(Integer issueId) {
        return commentRepository.findAllByIssueId(issueId).stream().map(commentMapper::toResponse).toList();
    }

    @Override
    public Comment onCreate(CommentRequest commentRequest, Comment e) {
        User user = userRepository.findByIdAndIsDeletedFalse(commentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        Issue issue = issueRepository.findByIdAndIsDeletedFalse(commentRequest.getIssueId())
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        e.setUser(user);
        e.setIssue(issue);
        return super.onCreate(commentRequest, e);
    }

    @Override
    public Comment onUpdate(CommentRequest commentRequest, Comment e) {
        return super.onUpdate(commentRequest, e);
    }
}
