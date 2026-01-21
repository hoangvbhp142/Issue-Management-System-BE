package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.response.IssuePrioritySummaryDto;
import com.example.issue_management_system.dto.response.IssueStatusSummaryDto;
import com.example.issue_management_system.dto.response.IssueTypeSummaryDto;
import com.example.issue_management_system.entity.enums.IssuePriority;
import com.example.issue_management_system.entity.enums.IssueStatus;
import com.example.issue_management_system.entity.enums.IssueType;
import com.example.issue_management_system.repository.IssueRepository;
import com.example.issue_management_system.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final IssueRepository issueRepository;

    @Override
    public IssueStatusSummaryDto getIssueStatusSummaryDto(Integer projectId) {

        List<Object []> rows = issueRepository.getIssueStatusSummary(projectId);

        Map<IssueStatus, Long> map = new EnumMap<>(IssueStatus.class);

        long total = 0;
        for (Object[] object: rows) {
            IssueStatus status = (IssueStatus) object[0];
            long count = (long) object[1];
            map.put(status, count);
            total += count;
        }

        long done = map.getOrDefault(IssueStatus.RESOLVED, 0L)
                + map.getOrDefault(IssueStatus.CLOSED, 0L);

        IssueStatusSummaryDto issueStatusSummaryDto = new IssueStatusSummaryDto();
        issueStatusSummaryDto.setTotal(total);
        issueStatusSummaryDto.setOpen(map.getOrDefault(IssueStatus.OPEN, 0L));
        issueStatusSummaryDto.setInProgress(map.getOrDefault(IssueStatus.IN_PROGRESS, 0L));
        issueStatusSummaryDto.setResolved(map.getOrDefault(IssueStatus.RESOLVED, 0L));
        issueStatusSummaryDto.setClosed(map.getOrDefault(IssueStatus.CLOSED, 0L));
        issueStatusSummaryDto.setDonePercent(total == 0 ? 0 : (long)((done * 100) / total));
        return issueStatusSummaryDto;
    }

    @Override
    public IssueTypeSummaryDto getIssueTypeSummaryDto(Integer projectId) {
        List<Object []> rows = issueRepository.getIssueTypeSummary(projectId);
        Map<IssueType, Long> map = new EnumMap<>(IssueType.class);
        for (Object[] object: rows) {
            IssueType type = (IssueType) object[0];
            long count = (long) object[1];
            map.put(type, count);
        }

        IssueTypeSummaryDto issueTypeSummaryDto = new IssueTypeSummaryDto();
        issueTypeSummaryDto.setBug(map.getOrDefault(IssueType.BUG, 0L));
        issueTypeSummaryDto.setTask(map.getOrDefault(IssueType.TASK, 0L));
        issueTypeSummaryDto.setStory(map.getOrDefault(IssueType.STORY, 0L));
        return issueTypeSummaryDto;
    }

    @Override
    public IssuePrioritySummaryDto getIssuePrioritySummaryDto(Integer projectId) {
        List<Object []> rows = issueRepository.getIssuePrioritySummary(projectId);
        Map<IssuePriority, Long> map = new EnumMap<>(IssuePriority.class);
        for (Object[] object: rows) {
            IssuePriority priority = (IssuePriority) object[0];
            long count = (long) object[1];
            map.put(priority, count);
        }

        IssuePrioritySummaryDto issuePrioritySummaryDto = new IssuePrioritySummaryDto();
        issuePrioritySummaryDto.setLow(map.getOrDefault(IssuePriority.LOW, 0L));
        issuePrioritySummaryDto.setMedium(map.getOrDefault(IssuePriority.MEDIUM, 0L));
        issuePrioritySummaryDto.setHigh(map.getOrDefault(IssuePriority.HIGH, 0L));
        issuePrioritySummaryDto.setCritical(map.getOrDefault(IssuePriority.CRITICAL, 0L));
        return issuePrioritySummaryDto;
    }
}
