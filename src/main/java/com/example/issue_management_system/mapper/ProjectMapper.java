package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import com.example.issue_management_system.dto.ProjectDto;
import com.example.issue_management_system.entity.Project;
import com.example.issue_management_system.request.ProjectRequest;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper extends BaseMapper<Integer, Project, ProjectRequest, ProjectDto> {
}
