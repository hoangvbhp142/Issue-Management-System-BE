package com.example.issue_management_system.service;

import com.example.issue_management_system.dto.response.IssueImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ImageIssueService {

    void uploadImage(Integer issueId, List<MultipartFile> files);

    InputStream getImage(String objectKey);

    List<IssueImageDto> findImageByIssueId(Integer issueId);
}
