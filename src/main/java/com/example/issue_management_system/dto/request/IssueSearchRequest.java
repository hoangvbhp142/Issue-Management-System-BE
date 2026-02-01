package com.example.issue_management_system.dto.request;

import com.example.issue_management_system.entity.enums.IssuePriority;
import com.example.issue_management_system.entity.enums.IssueStatus;
import com.example.issue_management_system.entity.enums.IssueType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueSearchRequest implements Serializable {
    String keyword;
    Set<IssueStatus> statuses;
    Set<IssueType> types;
    Integer assigneeId;
    Set<IssuePriority> priorities;
    LocalDateTime createdFrom;
    LocalDateTime createdTo;

    int pageSize = 10;
    int pageNum = 0;
    String sortBy = "createdAt";
    Sort.Direction direction = Sort.Direction.DESC;
}
