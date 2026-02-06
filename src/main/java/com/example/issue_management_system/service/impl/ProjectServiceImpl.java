package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.entity.enums.ProjectRole;
import com.example.issue_management_system.dto.response.ProjectDto;
import com.example.issue_management_system.entity.Project;
import com.example.issue_management_system.entity.ProjectMember;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.entity.enums.ProjectStatus;
import com.example.issue_management_system.exception.BusinessException;
import com.example.issue_management_system.mapper.ProjectMapper;
import com.example.issue_management_system.repository.ProjectMemberRepository;
import com.example.issue_management_system.repository.ProjectRepository;
import com.example.issue_management_system.dto.request.ProjectRequest;
import com.example.issue_management_system.service.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl extends BaseServiceImpl<Project, Integer, ProjectRequest, ProjectDto>
        implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    private final ProjectMemberRepository memberRepository;

    private final UserServiceImpl userService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, ProjectMemberRepository memberRepository, UserServiceImpl userService) {
        super(projectRepository, projectMapper);
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.memberRepository = memberRepository;
        this.userService = userService;
    }

    @Override
    public void archiveProject(Integer projectId, Integer currentUserId) {

        //dang phan van nen de lai

        Project project = findById(projectId);

        if (!project.getOwner().getId().equals(currentUserId)) {
            throw new BusinessException("Khong duoc phep thuc hien");
        }

    }

    @Override
    public List<ProjectDto> findProjectVisibleToUser() {
        User user = userService.getUserAuthentication();
        List<Object[]> result = projectRepository.findProjectsVisibleToUser(user.getId());

        List<ProjectDto> dtos = new ArrayList<>();

        for (Object[] objects: result) {

            int issues = ((Number) objects[4]).intValue();
            int members = ((Number) objects[5]).intValue();
            int completed = ((Number) objects[6]).intValue();

            ProjectDto dto = new ProjectDto();

            dto.setId((Integer) objects[0]);
            dto.setName((String) objects[1]);
            dto.setDescription((String) objects[2]);
            dto.setIssues(issues);
            dto.setMembers(members);
            dto.setCompleted(completed);
            dto.setProgress(issues == 0 ? 0 : (int) (completed * 100 / issues));
            dto.setStatus(ProjectStatus.valueOf((String) objects[3]));

            dto.setCode((String) objects[7]);

            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    @Override
    public ProjectDto create(ProjectRequest projectRequest) {
        Project project = projectMapper.createToEntity(projectRequest);

        User owner = userService.getUserAuthentication();
        project.setOwner(owner);
        project.setStatus(ProjectStatus.PLANNING);

        Project savedProject = projectRepository.save(project);

        ProjectMember member = new ProjectMember();
        member.setRole(ProjectRole.OWNER);
        member.setUser(owner);
        member.setProject(savedProject);

        memberRepository.save(member);

        return projectMapper.toResponse(savedProject);
    }

    @Override
    public Project onCreate(ProjectRequest projectRequest, Project e) {
        return super.onCreate(projectRequest, e);
    }

    @Override
    public Project onUpdate(ProjectRequest projectRequest, Project e) {
        User owner = userService.getUserAuthentication();
        e.setOwner(owner);
        e.setStatus(projectRequest.getStatus());
        return super.onUpdate(projectRequest, e);
    }
}
