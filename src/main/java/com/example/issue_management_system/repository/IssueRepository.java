package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.Issue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends BaseRepository<Issue, Integer> {
    List<Issue> findAllByProjectId(Integer projectId);
}
