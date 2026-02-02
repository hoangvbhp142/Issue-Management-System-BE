package com.example.issue_management_system.service;

import com.example.issue_management_system.dto.response.AttachmentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface AttachmentService {

    void upload(Integer issueId, List<MultipartFile> files);

    InputStream getAttachment(String objectKey);

    List<AttachmentDto> findAttachmentByIssueId(Integer issueId);
}
