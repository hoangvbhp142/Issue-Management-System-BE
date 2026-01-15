package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.common.enums.IssueStatus;
import com.example.issue_management_system.dto.IssueDto;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.IssueHistory;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.mapper.IssueMapper;
import com.example.issue_management_system.repository.IssueRepository;
import com.example.issue_management_system.request.IssueRequest;
import com.example.issue_management_system.service.IssueService;
import org.springframework.stereotype.Service;

@Service
public class IssueServiceImpl extends BaseServiceImpl<Issue, Integer, IssueRequest, IssueDto> implements IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    private final UserServiceImpl userService;

    public IssueServiceImpl(IssueRepository issueRepository,
                            IssueMapper issueMapper,
                            UserServiceImpl userService) {
        super(issueRepository, issueMapper);
        this.issueRepository = issueRepository;
        this.issueMapper = issueMapper;
        this.userService = userService;
    }

    @Override
    public Issue assignIssue(Integer issueId, Integer assigneeId) {
        User assignee = userService.findById(assigneeId);
        Issue issue = findById(issueId);
        issue.setAssignee(assignee);
        return issueRepository.save(issue);
    }

    @Override
    public Issue changeStatus(Integer issueId, IssueStatus newStatus, Integer userId) {
        User changeBy = userService.findById(userId);
        Issue issue = findById(issueId);

        IssueStatus oldStatus = issue.getStatus();

        issue.setStatus(newStatus);

        IssueHistory history = new IssueHistory();
        history.setIssue(issue);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setChangedBy(changeBy);


        return issueRepository.save(issue);
    }
}
