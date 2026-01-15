package com.example.issue_management_system.entity;

import com.example.issue_management_system.common.enums.IssueStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueHistory extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "issue_id")
    Issue issue;

    @Enumerated(EnumType.STRING)
    IssueStatus oldStatus;

    @Enumerated(EnumType.STRING)
    IssueStatus newStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User changedBy;

}
