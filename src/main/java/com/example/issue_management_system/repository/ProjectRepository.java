package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends BaseRepository<Project, Integer> {

    @Query("""
            SELECT p FROM Project p
            LEFT JOIN ProjectMember pm ON pm.project = p
            WHERE p.owner.id = :userId
                OR pm.user.id = :userId
            """)
    List<Project> findProjectsVisibleToUser(@Param("userId") Integer userId);
}
