package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.response.CommentDto;
import com.example.issue_management_system.entity.Comment;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.exception.NotFoundException;
import com.example.issue_management_system.mapper.CommentMapper;
import com.example.issue_management_system.repository.CommentRepository;
import com.example.issue_management_system.repository.IssueRepository;
import com.example.issue_management_system.repository.UserRepository;
import com.example.issue_management_system.dto.request.CommentRequest;
import com.example.issue_management_system.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, Integer, CommentRequest, CommentDto>
        implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final UserServiceImpl userService;
    private final IssueServiceImpl issueService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentMapper commentMapper,
                              UserServiceImpl userService,
                              IssueServiceImpl issueService) {
        super(commentRepository, commentMapper);
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
        this.issueService = issueService;
    }

    @Override
    public List<CommentDto> findAllByIssueId(Integer issueId) {
        return commentRepository.findAllByIssueId(issueId).stream().map(commentMapper::toResponse).toList();
    }

    @Override
    public Comment onCreate(CommentRequest commentRequest, Comment e) {
        User user = userService.getUserAuthentication();
        Issue issue = issueService.findById(commentRequest.getIssueId());

        e.setUser(user);
        e.setIssue(issue);
        return super.onCreate(commentRequest, e);
    }

    @Override
    public Comment onUpdate(CommentRequest commentRequest, Comment e) {
        return super.onUpdate(commentRequest, e);
    }
}
