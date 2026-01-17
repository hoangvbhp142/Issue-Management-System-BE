package com.example.issue_management_system.dto.response;

import com.example.issue_management_system.entity.enums.IssueStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueHistoryDto implements Serializable {
    Integer id;
    IssueDto issue;
    IssueStatus oldStatus;
    IssueStatus newStatus;
    UserDto changedBy;
    LocalDateTime updatedAt;
}
