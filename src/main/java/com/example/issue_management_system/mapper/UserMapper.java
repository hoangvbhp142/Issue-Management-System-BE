package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import com.example.issue_management_system.dto.UserDto;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.request.UserRequest;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper extends BaseMapper<Integer, User, UserRequest, UserDto> {
}
