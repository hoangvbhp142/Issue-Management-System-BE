package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.dto.response.AttachmentDto;
import com.example.issue_management_system.service.impl.AttachmentServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    private final AttachmentServiceImpl imageIssueService;

    @PostMapping(value = "/{id}/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> uploadImage(@PathVariable Integer id,
                                      @RequestParam List<MultipartFile> files
    ) {
        imageIssueService.upload(id, files);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Success"
        );
    }

    @GetMapping("/{issueId}/attachments")
    public ApiResponse<List<AttachmentDto>> getImagesByIssue(
            @PathVariable Integer issueId
    ) {
        List<AttachmentDto> images = imageIssueService.findAttachmentByIssueId(issueId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                images
        );
    }

    @PostConstruct
    public void debug() {
        System.out.println("MINIO URL = " + url);
        System.out.println("MINIO ACCESS = " + accessKey);
        System.out.println("MINIO SECRET = " + secretKey);
    }

}
