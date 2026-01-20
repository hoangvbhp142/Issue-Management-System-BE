package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.entity.enums.ProjectRole;
import com.example.issue_management_system.dto.response.ProjectMemberDto;
import com.example.issue_management_system.entity.Project;
import com.example.issue_management_system.entity.ProjectMember;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.exception.AlreadyExistException;
import com.example.issue_management_system.exception.BusinessException;
import com.example.issue_management_system.exception.NotFoundException;
import com.example.issue_management_system.mapper.ProjectMemberMapper;
import com.example.issue_management_system.repository.ProjectMemberRepository;
import com.example.issue_management_system.dto.request.ProjectMemberRequest;
import com.example.issue_management_system.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectMemberServiceImpl extends BaseServiceImpl<ProjectMember, Integer, ProjectMemberRequest, ProjectMemberDto>
        implements ProjectMemberService {

    private final ProjectMemberRepository memberRepository;
    private final ProjectMemberMapper memberMapper;

    private final ProjectServiceImpl projectService;
    private final UserServiceImpl userService;

    public ProjectMemberServiceImpl(ProjectMemberRepository memberRepository,
                                    ProjectMemberMapper memberMapper,
                                    ProjectServiceImpl projectService,
                                    UserServiceImpl userService) {
        super(memberRepository, memberMapper);
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public void addMember(Integer projectId, Integer userId) {
        User currentUser = userService.getUserAuthentication();
        checkRole(projectId, currentUser.getId(), ProjectRole.OWNER);

        if (isMember(projectId, userId)) {
            throw new AlreadyExistException("Da co trong nhom du an");
        }
        User user = userService.findById(userId);
        Project project = projectService.findById(projectId);

        ProjectMember projectMember = new ProjectMember();
        projectMember.setUser(user);
        projectMember.setProject(project);
        projectMember.setRole(ProjectRole.MEMBER);

        memberRepository.save(projectMember);
    }

    @Transactional
    @Override
    public void removeMember(Integer projectId, Integer userId) {
        User currentUser = userService.getUserAuthentication();
        checkRole(projectId, currentUser.getId(), ProjectRole.OWNER);
        if (userId.equals(currentUser.getId())) {
            throw new BusinessException("Khong the tu xoa ban than");
        }

        ProjectMember targetMember = memberRepository.findByProjectIdAndUserId(projectId, userId)
                        .orElseThrow(() -> new NotFoundException("Thanh vien khong co trong nhom"));

        if (targetMember.getRole().equals(ProjectRole.OWNER)) {
            if (memberRepository.countByProjectIdAndRole(projectId, ProjectRole.OWNER) <= 1) {
                throw new BusinessException("Khong the xoa lead cuoi dung");
            }
        }

        memberRepository.delete(targetMember);
    }

    @Transactional
    @Override
    public void changeRole(Integer projectId, Integer userId, ProjectRole newRole) {
        User currentUser = userService.getUserAuthentication();

        checkRole(projectId, currentUser.getId(), ProjectRole.OWNER);
        ProjectMember targetMember = memberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new NotFoundException("Thanh vien khong co trong nhom"));

        if (targetMember.getRole().equals(ProjectRole.OWNER)) {
            if (memberRepository.countByProjectIdAndRole(projectId, ProjectRole.OWNER) <= 1) {
                throw new BusinessException("Khong the thuc hien, day la owner cuoi cung");
            }

            if (!newRole.equals(ProjectRole.OWNER) && targetMember.getUser().getId().equals(currentUser.getId())) {
                throw new BusinessException("Khong the tu ha cap ban than");
            }
        }
        targetMember.setRole(newRole);
        memberRepository.save(targetMember);
    }

    @Override
    public boolean isMember(Integer projectId, Integer userId) {
        return memberRepository.existsByProjectIdAndUserId(projectId, userId);
    }

    @Override
    public void checkMember(Integer projectId, Integer userId) {
        if (!memberRepository.existsByProjectIdAndUserId(projectId, userId)) {
            throw new BusinessException("Khong phai thanh vien cua nhom");
        }
    }

    @Override
    public void checkRole(Integer projectId, Integer userId, ProjectRole requiredRole) {
        if (!memberRepository.existsByProjectIdAndUserIdAndRole(projectId, userId, requiredRole)) {
            throw new BusinessException("Khong duoc phep thuc hien");
        }
    }

    @Override
    public List<User> getMembers(Integer projectId) {
        return memberRepository.findAllByProjectId(projectId)
                .stream()
                .map(ProjectMember::getUser).toList();
    }
}
