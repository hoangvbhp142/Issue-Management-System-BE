package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Integer> {
}
