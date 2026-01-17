package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.entity.Project;
import com.example.issue_management_system.entity.enums.IssueStatus;
import com.example.issue_management_system.entity.enums.ProjectRole;
import com.example.issue_management_system.dto.response.IssueDto;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.IssueHistory;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.mapper.IssueMapper;
import com.example.issue_management_system.repository.IssueHistoryRepository;
import com.example.issue_management_system.repository.IssueRepository;
import com.example.issue_management_system.dto.request.IssueRequest;
import com.example.issue_management_system.service.IssueService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class IssueServiceImpl extends BaseServiceImpl<Issue, Integer, IssueRequest, IssueDto> implements IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    private final UserServiceImpl userService;
    private final IssueHistoryRepository historyRepository;

    private final ProjectMemberServiceImpl memberService;
    private final ProjectServiceImpl projectService;

    public IssueServiceImpl(IssueRepository issueRepository,
                            IssueMapper issueMapper,
                            UserServiceImpl userService,
                            IssueHistoryRepository historyRepository, ProjectMemberServiceImpl memberService, ProjectServiceImpl projectService) {
        super(issueRepository, issueMapper);
        this.issueRepository = issueRepository;
        this.issueMapper = issueMapper;
        this.userService = userService;
        this.historyRepository = historyRepository;
        this.memberService = memberService;
        this.projectService = projectService;
    }

    @Transactional
    @Override
    public Issue assignIssue(Integer issueId, Integer assigneeId) {
        Issue issue = findById(issueId);
        memberService.checkMember(issue.getProject().getId(), assigneeId);
        User assignee = userService.findById(assigneeId);
        issue.setAssignee(assignee);
        return issueRepository.save(issue);
    }

    @Transactional
    @Override
    public Issue changeStatus(Integer issueId, IssueStatus newStatus, Integer userId) {
        Issue issue = findById(issueId);

        User changeBy = userService.findById(userId);
        memberService.checkMember(issue.getProject().getId(), userId);

        if (!issue.getAssignee().getId().equals(userId)) {
            memberService.checkRole(issue.getProject().getId(), userId, ProjectRole.OWNER);
        }

        IssueStatus oldStatus = issue.getStatus();
        issue.setStatus(newStatus);

        IssueHistory history = new IssueHistory();
        history.setIssue(issue);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setChangedBy(changeBy);

        historyRepository.save(history);
        return issueRepository.save(issue);
    }

    @Override
    public Issue onCreate(IssueRequest issueRequest, Issue e) {
        User reporter = userService.findById(issueRequest.getReporterId());
        Project project = projectService.findById(issueRequest.getProjectId());

        memberService.checkMember(project.getId(), reporter.getId());


        e.setProject(project);
        e.setReporter(reporter);
        return super.onCreate(issueRequest, e);
    }

    @Override
    public Issue onUpdate(IssueRequest issueRequest, Issue e) {
        User reporter = userService.findById(issueRequest.getReporterId());

        e.setReporter(reporter);
        return super.onUpdate(issueRequest, e);
    }
}
