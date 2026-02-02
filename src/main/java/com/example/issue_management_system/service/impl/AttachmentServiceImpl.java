package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.dto.response.AttachmentDto;
import com.example.issue_management_system.entity.Attachment;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.enums.RenderMode;
import com.example.issue_management_system.mapper.AttachmentMapper;
import com.example.issue_management_system.repository.AttachmentRepository;
import com.example.issue_management_system.service.AttachmentService;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.expiry}")
    private int expiry;

    @Value(("${minio.url}"))
    private String minioUrl;

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    private final MinioClient minioClient;
    private final IssueServiceImpl issueService;
    private final ProjectMemberServiceImpl memberService;

    @Transactional
    @Override
    public void upload(Integer issueId, List<MultipartFile> files) {
        List<Attachment> attachments = new ArrayList<>();
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

                Attachment attachment = getAttachment(file, objectKey, issue);
                attachments.add(attachment);
            }

            attachmentRepository.saveAll(attachments);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }

    }

    @NotNull
    private Attachment getAttachment(MultipartFile file, String objectKey, Issue issue) {
        Attachment attachment = new Attachment();
        attachment.setObjectKey(objectKey);
        attachment.setIssue(issue);
        attachment.setContentType(file.getContentType());
        attachment.setSize(file.getSize());
        attachment.setFileName(file.getOriginalFilename());

        attachment.setMode(
                Objects.requireNonNull(file.getContentType()).startsWith("image/")
                ? RenderMode.INLINE
                : file.getContentType().equals("application/pdf")
                    ? RenderMode.PREVIEW
                    : RenderMode.DOWNLOAD
        );
        return attachment;
    }

    @Override
    public InputStream getAttachment(String objectKey) {
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
    public List<AttachmentDto> findAttachmentByIssueId(Integer issueId) {
        return attachmentRepository.findByIssueId(issueId).stream().map(this::toDto).toList();
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

    private AttachmentDto toDto(Attachment attachment) {
        AttachmentDto dto = new AttachmentDto();
        dto.setId(attachment.getId());
        dto.setUrl(generatePresignedUrl(attachment.getObjectKey()));
        dto.setFileName(attachment.getFileName());
        dto.setContentType(attachment.getContentType());
        dto.setSize(attachment.getSize());
        dto.setMode(attachment.getMode());
        return dto;
    }
}
