package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.Attachment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends BaseRepository<Attachment, Integer> {
    List<Attachment> findByIssueId(Integer issueId);
}
