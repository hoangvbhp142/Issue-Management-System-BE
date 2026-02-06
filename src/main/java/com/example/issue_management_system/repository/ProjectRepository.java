package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends BaseRepository<Project, Integer> {

    @Query(value = """
            select p.id,
            	p.name ,
            	p.description ,
            	p.status ,
            	COALESCE(ic.issue_count , 0) as issues,
            	COALESCE(pmc.member_count , 0) as members,
            	COALESCE(cic.completed_issue_count , 0) as completed,
            	p.code
            from project p
            left join (
            	select project_id , count(*) as issue_count
            	from issue
            	group by project_id
            ) as ic on ic.project_id = p.id
            left join (
            	select project_id , count(*) as member_count
            	from project_member
            	group by project_id
            ) as pmc on pmc.project_id = p.id
            left join (
            	select project_id , count(*) as completed_issue_count
            	from issue
            	where status = 'CLOSED' or status = 'RESOLVED'
            	group by project_id
            ) as cic on cic.project_id = p.id
            where EXISTS (
            	select 1 from project_member pm
            	where pm.project_id = p.id and pm.user_id = :userId
            )
            """, nativeQuery = true)
    List<Object[]> findProjectsVisibleToUser(@Param("userId") Integer userId);
}
