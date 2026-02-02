package com.example.issue_management_system.mapper;

import com.example.issue_management_system.config.MapperConfig;
import com.example.issue_management_system.dto.response.AttachmentDto;
import com.example.issue_management_system.entity.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

@Mapper(config = MapperConfig.class)
public abstract class AttachmentMapper {

    @Value("${minio.url}")
    protected String minioUrl;

    @Value("${minio.bucket}")
    protected String bucket;

    @Mapping(target = "url", source = "objectKey", qualifiedByName = "buildUrl")
    public abstract AttachmentDto toDto(Attachment entity);

    @Named("buildUrl")
    protected String buildUrl(String objectKey) {
        return minioUrl + "/" + bucket + "/" + objectKey;
    }
}
