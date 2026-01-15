package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.IssueHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueHistoryRepository extends BaseRepository<IssueHistory, Integer> {
}
