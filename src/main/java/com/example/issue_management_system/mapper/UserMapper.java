package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper<ID, T, R, Q> extends BaseMapper<ID, T, R, Q> {
}
