package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.response.IssueHistoryDto;
import com.example.issue_management_system.entity.IssueHistory;
import com.example.issue_management_system.mapper.IssueHistoryMapper;
import com.example.issue_management_system.repository.IssueHistoryRepository;
import com.example.issue_management_system.dto.request.IssueHistoryRequest;
import com.example.issue_management_system.service.IssueHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IssueHistoryServiceImpl extends BaseServiceImpl<IssueHistory, Integer, IssueHistoryRequest, IssueHistoryDto>
        implements IssueHistoryService {

    private final IssueHistoryRepository historyRepository;
    private final IssueHistoryMapper historyMapper;

    public IssueHistoryServiceImpl(IssueHistoryRepository historyRepository, IssueHistoryMapper historyMapper) {
        super(historyRepository, historyMapper);
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    @Override
    public IssueHistoryDto create(IssueHistory issueHistory) {
        return historyMapper.toResponse(historyRepository.save(issueHistory));
    }

    @Override
    public Page<IssueHistoryDto> findAllByIssueId(Integer issueId, Pageable pageable) {
        return historyRepository.findAllByIssueId(issueId, pageable).map(historyMapper::toResponse);
    }

    @Override
    public Page<IssueHistoryDto> findAllByProjectId(Integer projectId, Pageable pageable) {
        return historyRepository.findAllByProjectId(projectId, pageable).map(historyMapper::toResponse);
    }
}
