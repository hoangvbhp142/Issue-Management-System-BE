package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.common.enums.IssueStatus;
import com.example.issue_management_system.dto.IssueDto;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.IssueHistory;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.mapper.IssueMapper;
import com.example.issue_management_system.repository.IssueHistoryRepository;
import com.example.issue_management_system.repository.IssueRepository;
import com.example.issue_management_system.repository.UserRepository;
import com.example.issue_management_system.request.IssueRequest;
import com.example.issue_management_system.service.IssueService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class IssueServiceImpl extends BaseServiceImpl<Issue, Integer, IssueRequest, IssueDto> implements IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    private final UserRepository userRepository;
    private final IssueHistoryRepository historyRepository;

    public IssueServiceImpl(IssueRepository issueRepository,
                            IssueMapper issueMapper,
                            UserRepository userRepository,
                            IssueHistoryRepository historyRepository) {
        super(issueRepository, issueMapper);
        this.issueRepository = issueRepository;
        this.issueMapper = issueMapper;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    @Transactional
    @Override
    public Issue assignIssue(Integer issueId, Integer assigneeId) {
        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Issue issue = findById(issueId);
        issue.setAssignee(assignee);
        return issueRepository.save(issue);
    }

    @Transactional
    @Override
    public Issue changeStatus(Integer issueId, IssueStatus newStatus, Integer userId) {
        User changeBy = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Issue issue = findById(issueId);

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
        User reporter = userRepository.findById(issueRequest.getReporterId())
                .orElseThrow(() -> new RuntimeException("Khong tin thay nguoi dung"));

        e.setReporter(reporter);
        return super.onCreate(issueRequest, e);
    }

    @Override
    public Issue onUpdate(IssueRequest issueRequest, Issue e) {
        User reporter = userRepository.findById(issueRequest.getReporterId())
                .orElseThrow(() -> new RuntimeException("Khong tin thay nguoi dung"));

        e.setReporter(reporter);
        return super.onUpdate(issueRequest, e);
    }
}
