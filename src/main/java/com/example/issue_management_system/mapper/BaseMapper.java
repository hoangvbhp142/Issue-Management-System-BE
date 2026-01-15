package com.example.issue_management_system.mapper;


import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @param <ID> ID
 * @param <T> entity
 * @param <R> request
 * @param <Q> response
 **/
public interface BaseMapper<ID, T, R, Q> {

    T createToEntity(R request);

    @Mapping(target = "id", ignore = true)
    T updateToEntity(@MappingTarget T entity, R request);

    Q toResponse(T entity);

    T toEntity(Q dto);
}
