package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import com.example.issue_management_system.dto.response.IssueImageDto;
import com.example.issue_management_system.service.impl.ImageIssueServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class IssueImageController {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    private final ImageIssueServiceImpl imageIssueService;

    @PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> uploadImage(@PathVariable Integer id,
                                      @RequestParam List<MultipartFile> files
    ) {
        imageIssueService.uploadImage(id, files);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Success"
        );
    }

    @GetMapping("/{issueId}/images")
    public ApiResponse<List<IssueImageDto>> getImagesByIssue(
            @PathVariable Integer issueId
    ) {
        List<IssueImageDto> images = imageIssueService.findImageByIssueId(issueId);
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
