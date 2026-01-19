package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import com.example.issue_management_system.dto.request.RoleRequest;
import com.example.issue_management_system.dto.response.RoleDto;
import com.example.issue_management_system.entity.Role;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface RoleMapper extends BaseMapper<Integer, Role, RoleRequest, RoleDto> {
}
