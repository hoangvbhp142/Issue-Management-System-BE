package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.IssueImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueImageRepository extends BaseRepository<IssueImage, Integer> {
    List<IssueImage> findByIssueId(Integer issueId);
}
