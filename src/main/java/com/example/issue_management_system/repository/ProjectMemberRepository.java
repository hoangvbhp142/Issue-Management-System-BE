package com.example.issue_management_system.repository;

import com.example.issue_management_system.common.enums.ProjectRole;
import com.example.issue_management_system.entity.ProjectMember;
import com.example.issue_management_system.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends BaseRepository<ProjectMember, Integer> {
    boolean existsByProjectIdAndUserId(Integer projectId, Integer userId);
    boolean existsByProjectIdAndUserIdAndRole(Integer projectId, Integer userId, ProjectRole requiredRole);

    Optional<ProjectMember> findByProjectIdAndUserId(Integer projectId, Integer userId);

    int countByProjectIdAndRole(Integer projectId, ProjectRole projectRole);

    List<ProjectMember> findAllByProjectId(Integer projectId);
}
