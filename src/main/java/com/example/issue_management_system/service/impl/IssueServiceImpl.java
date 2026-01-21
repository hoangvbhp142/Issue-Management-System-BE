package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.response.BoardDto;
import com.example.issue_management_system.entity.Project;
import com.example.issue_management_system.entity.enums.IssueStatus;
import com.example.issue_management_system.entity.enums.ProjectRole;
import com.example.issue_management_system.dto.response.IssueDto;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.IssueHistory;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.exception.BusinessException;
import com.example.issue_management_system.exception.NotFoundException;
import com.example.issue_management_system.mapper.IssueMapper;
import com.example.issue_management_system.repository.IssueHistoryRepository;
import com.example.issue_management_system.repository.IssueRepository;
import com.example.issue_management_system.dto.request.IssueRequest;
import com.example.issue_management_system.service.IssueService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IssueServiceImpl extends BaseServiceImpl<Issue, Integer, IssueRequest, IssueDto>
        implements IssueService {

    private final Map<IssueStatus, List<IssueStatus>> STATUS_FLOW = Map.of(
            IssueStatus.OPEN, List.of(IssueStatus.IN_PROGRESS),
            IssueStatus.IN_PROGRESS, List.of(IssueStatus.RESOLVED),
            IssueStatus.RESOLVED, List.of(IssueStatus.CLOSED),
            IssueStatus.CLOSED, List.of(IssueStatus.OPEN)
    );


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
    public IssueDto assignIssue(Integer issueId, Integer assigneeId) {
        Issue issue = issueRepository.findByIdForUpdate(issueId)
                .orElseThrow(() -> new NotFoundException("Khong tim thay issue"));

        User currentUser = userService.getUserAuthentication();
        memberService.checkMember(issue.getProject().getId(), assigneeId);

        if (!currentUser.getId().equals(assigneeId)) {
            memberService.checkRole(issue.getProject().getId(), currentUser.getId(), ProjectRole.OWNER);
        }

        User assignee = userService.findById(assigneeId);
        issue.setAssignee(assignee);
        return issueMapper.toResponse(issueRepository.save(issue));
    }

    @Transactional
    @Override
    public IssueDto changeStatus(Integer issueId, IssueStatus newStatus) {
        Issue issue = findById(issueId);
        User changeBy = userService.getUserAuthentication();

        validateStatusFlow(issue, newStatus);
        validateChangeStatusPermission(issue, changeBy.getId());

        IssueStatus oldStatus = issue.getStatus();
        issue.setStatus(newStatus);

        IssueHistory history = new IssueHistory();
        history.setIssue(issue);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setChangedBy(changeBy);

        historyRepository.save(history);
        return issueMapper.toResponse(issueRepository.save(issue));
    }

    @Override
    public List<IssueDto> findAllByProjectId(Integer projectId) {
        return issueRepository.findAllByProjectId(projectId).stream().map(issueMapper::toResponse).toList();
    }

    @Override
    public Map<IssueStatus, List<IssueDto>> getBoardByProjectId(Integer projectId) {
        List<IssueDto> issueList = findAllByProjectId(projectId);
        Map<IssueStatus, List<IssueDto>> map = new HashMap<>();

        issueList.forEach(issueDto -> {
            if (!map.containsKey(issueDto.getStatus())) {
                map.put(issueDto.getStatus(), new ArrayList<>());
                map.get(issueDto.getStatus()).add(issueDto);
            }
            else {
                map.get(issueDto.getStatus()).add(issueDto);
            }
        });

        return map;
    }

    @Override
    public Issue onCreate(IssueRequest issueRequest, Issue e) {
        User reporter = userService.getUserAuthentication();
        Project project = projectService.findById(issueRequest.getProjectId());

        memberService.checkMember(project.getId(), reporter.getId());

        e.setProject(project);
        e.setReporter(reporter);
        return super.onCreate(issueRequest, e);
    }

    @Override
    public Issue onUpdate(IssueRequest issueRequest, Issue e) {
        User reporter = userService.getUserAuthentication();
        e.setReporter(reporter);
        return super.onUpdate(issueRequest, e);
    }

    private void validateStatusFlow(Issue issue, IssueStatus newStatus) {
        IssueStatus oldStatus = issue.getStatus();
        if (!STATUS_FLOW.getOrDefault(oldStatus, List.of()).contains(newStatus)) {
            throw new BusinessException("Trang thai khong hop le");
        }
    }

    private void validateChangeStatusPermission(Issue issue, Integer userId) {
        if (issue.getAssignee() != null && issue.getAssignee().getId().equals(userId)) {
            return;
        }
        memberService.checkRole(issue.getProject().getId(), userId, ProjectRole.OWNER);
    }
}
