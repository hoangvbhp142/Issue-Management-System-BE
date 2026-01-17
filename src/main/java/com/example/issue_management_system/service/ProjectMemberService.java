package com.example.issue_management_system.service;

import com.example.issue_management_system.common.enums.ProjectRole;
import com.example.issue_management_system.entity.User;

import java.util.List;

public interface ProjectMemberService {

    void addMember(Integer projectId, Integer userId, Integer currentUserId);
    void removeMember(Integer projectId, Integer userId, Integer currentUserId);
    void changeRole(Integer projectId, Integer userId, Integer currentUserId, ProjectRole newRole);
    boolean isMember(Integer projectId, Integer userId);
    void checkMember(Integer projectId, Integer userId);
    void checkRole(Integer projectId, Integer userId, ProjectRole requiredRole);
    List<User> getMembers(Integer projectId);

}
