package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.Issue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends BaseRepository<Issue, Integer> {
    List<Issue> findAllByProjectId(Integer projectId);

    @Query("""
            SELECT i.status, count(i)
            FROM Issue i
            WHERE i.project.id = :projectId
            GROUP BY i.status
            """)
    List<Object []> getIssueStatusSummary(@Param("projectId") Integer projectId);

    @Query("""
            SELECT i.type, count(i)
            FROM Issue i
            WHERE i.project.id = :projectId
            GROUP BY i.type
            """)
    List<Object []> getIssueTypeSummary(@Param("projectId") Integer projectId);

    @Query("""
            SELECT i.priority, count(i)
            FROM Issue i
            WHERE i.project.id = :projectId
            GROUP BY i.priority
            """)
    List<Object []> getIssuePrioritySummary(@Param("projectId") Integer projectId);
}
