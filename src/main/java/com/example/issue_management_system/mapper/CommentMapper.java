package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import com.example.issue_management_system.dto.response.CommentDto;
import com.example.issue_management_system.entity.Comment;
import com.example.issue_management_system.dto.request.CommentRequest;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CommentMapper extends BaseMapper<Integer, Comment, CommentRequest, CommentDto> {
}
