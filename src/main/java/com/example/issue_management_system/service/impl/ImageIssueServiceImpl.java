package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.response.IssueImageDto;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.IssueImage;
import com.example.issue_management_system.mapper.IssueImageMapper;
import com.example.issue_management_system.repository.IssueImageRepository;
import com.example.issue_management_system.service.ImageIssueService;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageIssueServiceImpl implements ImageIssueService {

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.expiry}")
    private int expiry;

    @Value(("${minio.url}"))
    private String minioUrl;

    private final IssueImageRepository issueImageRepository;
    private final IssueImageMapper issueImageMapper;

    private final MinioClient minioClient;
    private final IssueServiceImpl issueService;
    private final ProjectMemberServiceImpl memberService;

    @Transactional
    @Override
    public void uploadImage(Integer issueId, List<MultipartFile> files) {
        List<IssueImage> issueImages = new ArrayList<>();
        Issue issue = issueService.findById(issueId);

        try {
            for (MultipartFile file: files) {
                String objectKey = "projects/" + issue.getProject().getId()
                        + "/issues/" + issue.getId()
                        + "/" + UUID.randomUUID();

                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectKey)
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );

                IssueImage issueImage = new IssueImage();
                issueImage.setObjectKey(objectKey);
                issueImage.setIssue(issue);

                issueImages.add(issueImage);
            }

            issueImageRepository.saveAll(issueImages);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public InputStream getImage(String objectKey) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectKey)
                            .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | ServerException | XmlParserException | NoSuchAlgorithmException |
                 IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IssueImageDto> findImageByIssueId(Integer issueId) {
        return issueImageRepository.findByIssueId(issueId).stream().map(this::toDto).toList();
    }

    private String generatePresignedUrl(String objectKey) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(objectKey)
                            .expiry(expiry)
                            .build()
            );
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | ServerException | XmlParserException | NoSuchAlgorithmException |
                 IOException e) {
            throw new RuntimeException(e);
        }
    }

    private IssueImageDto toDto(IssueImage issueImage) {
        IssueImageDto dto = new IssueImageDto();
        dto.setId(issueImage.getId());
        dto.setUrl(generatePresignedUrl(issueImage.getObjectKey()));
        return dto;
    }
}
