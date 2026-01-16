package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import com.example.issue_management_system.dto.IssueHistoryDto;
import com.example.issue_management_system.entity.IssueHistory;
import com.example.issue_management_system.request.IssueHistoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface IssueHistoryMapper extends BaseMapper<Integer,IssueHistory, IssueHistoryRequest, IssueHistoryDto> {

    @Mapping(target = "changedBy", ignore = true)
    @Mapping(target = "newStatus", source = "request.newStatus")
    @Override
    IssueHistory createToEntity(IssueHistoryRequest request);

    @Mapping(target = "changedBy", ignore = true)
    @Mapping(target = "newStatus", source = "request.newStatus")
    @Override
    IssueHistory updateToEntity(IssueHistory entity, IssueHistoryRequest request);

    @Override
    IssueHistoryDto toResponse(IssueHistory entity);

    @Override
    IssueHistory toEntity(IssueHistoryDto dto);
}
