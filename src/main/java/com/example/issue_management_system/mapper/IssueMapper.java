package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import com.example.issue_management_system.dto.IssueDto;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.request.IssueRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface IssueMapper extends BaseMapper<Integer, Issue, IssueRequest, IssueDto> {

    @Mapping(target = "projectId", source = "project.id")
    @Override
    IssueDto toResponse(Issue entity);
}
