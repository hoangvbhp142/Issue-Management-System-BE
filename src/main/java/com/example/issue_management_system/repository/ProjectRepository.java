package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends BaseRepository<Project, Integer> {
}
