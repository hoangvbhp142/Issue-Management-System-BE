package com.example.issue_management_system.repository;

import com.example.issue_management_system.dto.response.IssueHistoryDto;
import com.example.issue_management_system.entity.IssueHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueHistoryRepository extends BaseRepository<IssueHistory, Integer> {
    Page<IssueHistory> findAllByIssueId(Integer issueId, Pageable pageable);

    @Query(
            """
            SELECT h
            FROM IssueHistory h
            JOIN h.issue i
            JOIN i.project p
            WHERE p.id = :projectId
            ORDER BY h.updatedAt DESC
            """
    )
    Page<IssueHistory> findAllByProjectId(@Param("projectId") Integer projectId, Pageable pageable);
}
