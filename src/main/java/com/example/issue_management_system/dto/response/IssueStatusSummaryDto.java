package com.example.issue_management_system.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueStatusSummaryDto {
    long total;
    long open;
    long inProgress;
    long resolved;
    long closed;
    long donePercent;
}
