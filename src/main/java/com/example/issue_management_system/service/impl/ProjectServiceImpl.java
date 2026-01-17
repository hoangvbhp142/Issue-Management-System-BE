package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.common.enums.ProjectRole;
import com.example.issue_management_system.dto.ProjectDto;
import com.example.issue_management_system.entity.Project;
import com.example.issue_management_system.entity.ProjectMember;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.mapper.ProjectMapper;
import com.example.issue_management_system.repository.ProjectMemberRepository;
import com.example.issue_management_system.repository.ProjectRepository;
import com.example.issue_management_system.request.ProjectRequest;
import com.example.issue_management_system.service.ProjectService;
import com.example.issue_management_system.service.UserService;
import org.springframework.stereotype.Service;

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
            throw new RuntimeException("Khong duoc phep thuc hien");
        }

    }

    @Override
    public ProjectDto create(ProjectRequest projectRequest) {

        Project project = projectMapper.createToEntity(projectRequest);

        User owner = userService.findById(projectRequest.getOwnerId());
        project.setOwner(owner);

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
        User owner = userService.findById(projectRequest.getOwnerId());
        e.setOwner(owner);
        return super.onUpdate(projectRequest, e);
    }
}
