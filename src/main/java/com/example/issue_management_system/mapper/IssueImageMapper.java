package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import com.example.issue_management_system.dto.response.IssueImageDto;
import com.example.issue_management_system.entity.IssueImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

@Mapper(config = MapperConfig.class)
public abstract class IssueImageMapper {

    @Value("${minio.url}")
    protected String minioUrl;

    @Value("${minio.bucket}")
    protected String bucket;

    @Mapping(target = "url", expression = "java(buildUrl(entity.getObjectKey()))")
    public abstract IssueImageDto toDto(IssueImage entity);

    protected String buildUrl(String objectKey) {
        return minioUrl + "/" + bucket + "/" + objectKey;
    }
}
