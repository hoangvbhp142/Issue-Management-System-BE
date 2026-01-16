package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.IssueHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueHistoryRepository extends BaseRepository<IssueHistory, Integer> {
    Page<IssueHistory> findAllByIssueId(Integer issueId, Pageable pageable);
}
