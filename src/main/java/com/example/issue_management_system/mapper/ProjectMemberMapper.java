package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import com.example.issue_management_system.dto.ProjectMemberDto;
import com.example.issue_management_system.entity.ProjectMember;
import com.example.issue_management_system.request.ProjectMemberRequest;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ProjectMemberMapper extends BaseMapper<Integer, ProjectMember, ProjectMemberRequest, ProjectMemberDto> {
}
