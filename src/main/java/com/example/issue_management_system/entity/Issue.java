package com.example.issue_management_system.entity;

import com.example.issue_management_system.entity.enums.IssueStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Issue extends BaseEntity{

    String title;
    String description;

    @Enumerated(EnumType.STRING)
    IssueStatus status;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    User reporter;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    User assignee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    List<IssueImage> issueImages;
}
