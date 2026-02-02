package com.example.issue_management_system.dto.response;

import com.example.issue_management_system.entity.enums.RenderMode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachmentDto {
    Integer id;
    String url;
    String contentType;
    String fileName;
    Long size;
    RenderMode mode;
}
