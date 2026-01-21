package com.example.issue_management_system.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssuePrioritySummaryDto {
    long low;
    long medium;
    long high;
    long critical;
}
