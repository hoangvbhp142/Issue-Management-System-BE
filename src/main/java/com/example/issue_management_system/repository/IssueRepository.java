package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.Issue;
import jakarta.persistence.LockModeType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends BaseRepository<Issue, Integer>, JpaSpecificationExecutor<Issue> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT i FROM Issue i WHERE i.id = :id
            """)
    Optional<Issue> findByIdForUpdate(@Param("id") Integer id);

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
