package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import com.example.issue_management_system.dto.request.AdminUserRequest;
import com.example.issue_management_system.dto.response.AdminUserDto;
import com.example.issue_management_system.entity.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AdminUserMapper extends BaseMapper<Integer, User, AdminUserRequest, AdminUserDto> {
}
